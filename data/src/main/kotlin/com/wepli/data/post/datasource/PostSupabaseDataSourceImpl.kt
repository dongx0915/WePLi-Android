package com.wepli.data.post.datasource

import com.wepli.core.kotlin.flow.FlowResult
import com.wepli.data.supabase.SupabaseConstants
import com.wepli.data.common.supabase.response.IdResponse
import com.wepli.data.di.qualifier.SupabaseDataSource
import com.wepli.data.post.request.PostBsideTrackRequestBody
import com.wepli.data.post.request.PostRequestBody
import com.wepli.data.post.request.toPostRequest
import com.wepli.data.post.request.mapToSongRequest
import com.wepli.data.post.response.PostResponse
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

    override fun getPosts(): FlowResult<List<PostResponse>> = flow {
        val result = runCatching {
            supabase.postgrest[SupabaseConstants.POST_VIEW].select().decodeList<PostResponse>()
        }

        emit(result)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun addPost(post: Post): FlowResult<Unit> {
        val postRequestBody = post.toPostRequest()
        val songRequestBody = post.mapToSongRequest()

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

    /**
     * addPost 내에서 insertPost, insertPostBsideTracks를 같이 수행하고 있음
     * 원래의 경우라면 Repository에서 여러 DataSource를 조합하는게 맞지만, 현재는 서버 대용이라 앱에서 해당 로직을 구현하고 있어 DataSource에 통합
     * 추후 서버로 마이그레이션 시 addPost만 사용하게 되기 때문
     */
    private fun insertPost(post: PostRequestBody) = flow {
        val result = runCatching {
            with(supabase.postgrest[SupabaseConstants.POST_TABLE]) {
                insert(post) {
                    select(columns = Columns.list("id"))
                }.decodeSingle<IdResponse>()
            }.id
        }

        emit(result)
    }

    private fun insertPostBsideTracks(bSideTracks: List<PostBsideTrackRequestBody>) = flow {
        val result = runCatching {
            supabase.postgrest[SupabaseConstants.POST_BSIDE_TRACK_TABLE].insert(bSideTracks)
        }

        emit(result)
    }
}