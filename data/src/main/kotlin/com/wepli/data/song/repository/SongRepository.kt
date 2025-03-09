package com.wepli.data.song.repository

import com.wepli.core.kotlin.flow.FlowResult
import com.wepli.data.song.request.SongRequestBody

interface SongRepository {

    fun upsertSongs(song: List<SongRequestBody>): FlowResult<Unit>
}