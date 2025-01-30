package com.wepli.data.song.datasource

import com.wepli.core.kotlin.FlowResult
import com.wepli.data.SupabaseTable
import com.wepli.data.common.supabase.response.IdResponse
import com.wepli.data.song.request.SongRequestBody
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SongSupabaseDataSourceImpl @Inject constructor(
    private val supabase: SupabaseClient,
) : SongDataSource {

    override fun upsertSongs(song: List<SongRequestBody>): FlowResult<List<IdResponse>> = flow {
        val result = runCatching {
            supabase.postgrest[SupabaseTable.SONG_TABLE].upsert(song) {
                onConflict = "song_id"
                select(columns = Columns.list("id"))
            }.decodeList<IdResponse>()
        }

        emit(result)
    }
}