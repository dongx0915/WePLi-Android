package com.wepli.data.artist.datasource.remote

import com.wepli.data.artist.ArtistApi
import com.wepli.data.artist.response.ArtistListResponse
import com.wepli.core.kotlin.flow.FlowResult
import javax.inject.Inject

class ArtistDataSourceImpl @Inject constructor(
    private val artistApi: ArtistApi
) : ArtistDataSource {

    override fun getArtists(): FlowResult<ArtistListResponse> {
        return artistApi.getArtists()
    }
}