package com.wepli.data.post.datasource

import android.util.Log
import com.wepli.core.kotlin.FlowResult
import com.wepli.data.SupabaseTable
import com.wepli.data.common.supabase.response.IdResponse
import com.wepli.data.di.qualifier.SupabaseDataSource
import com.wepli.data.post.request.PostBsideTrackRequestBody
import com.wepli.data.post.request.PostRequestBody
import com.wepli.data.post.request.toPostRequestBody
import com.wepli.data.post.request.toSongRequestBody
import com.wepli.data.song.datasource.SongDataSource
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
import model.community.Post
import javax.inject.Inject

class PostSupabaseDataSourceImpl @Inject constructor(
    private val supabase: SupabaseClient,
    @SupabaseDataSource private val songDataSource: SongDataSource,
) : PostDataSource {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun addPost(post: Post): FlowResult<Unit> {
        val postRequestBody = post.toPostRequestBody()
        val songRequestBody = post.toSongRequestBody()

        val postFlow = insertPost(postRequestBody)
        val songsFlow = songDataSource.upsertSongs(songRequestBody)

        return postFlow.zip(songsFlow) { postIdFlow, songIdsFlow ->
            val postId = postIdFlow.getOrThrow()
            val songIds = songIdsFlow.getOrThrow()

            val bSideTracks = songIds.map {
                PostBsideTrackRequestBody(
                    postId = postId,
                    songId = it.id
                )
            }

            insertPostBsideTracks(bSideTracks)
        }.flatMapConcat { it }
            .map { Result.success(Unit) }
            .flowOn(Dispatchers.IO)
            .catch {
                emit(Result.failure(it))
            }
    }

    private fun insertPost(post: PostRequestBody) = flow {
        val result = runCatching {
            with(supabase.postgrest[SupabaseTable.POST_TABLE]) {
                insert(post) {
                    select(columns = Columns.list("id"))
                }.decodeSingle<IdResponse>()
            }.id
        }

        emit(result)
    }

    private fun insertPostBsideTracks(bSideTracks: List<PostBsideTrackRequestBody>) = flow {
        val result = runCatching {
            supabase.postgrest[SupabaseTable.POST_BSIDE_TRACK_TABLE].insert(bSideTracks)
        }

        emit(result)
    }
}