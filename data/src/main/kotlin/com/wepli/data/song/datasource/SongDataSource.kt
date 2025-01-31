package com.wepli.data.song.datasource

import com.wepli.core.kotlin.FlowResult
import com.wepli.data.common.supabase.response.IdResponse
import com.wepli.data.song.request.SongRequestBody

interface SongDataSource {

    fun upsertSongs(song: List<SongRequestBody>): FlowResult<List<IdResponse>>
}