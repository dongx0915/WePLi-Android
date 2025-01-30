package com.wepli.data.song.repository

import com.wepli.core.kotlin.FlowResult
import com.wepli.data.network.toEntityResult
import com.wepli.data.post.request.SongRequestBody
import com.wepli.data.song.datasource.SongDataSource
import javax.inject.Inject

class SongRepositoryImpl @Inject constructor(
    private val songDataSource: SongDataSource,
): SongRepository {

    override fun upsertSongs(song: List<SongRequestBody>): FlowResult<Unit> {
        return songDataSource.upsertSongs(song).toEntityResult {}
    }
}