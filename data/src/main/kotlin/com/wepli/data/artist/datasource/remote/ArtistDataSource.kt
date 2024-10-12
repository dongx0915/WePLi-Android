package com.wepli.data.artist.datasource.remote

import com.wepli.data.artist.response.ArtistListResponse
import com.wepli.kotlin.FlowResult

interface ArtistDataSource {

    suspend fun getArtists(): FlowResult<ArtistListResponse>
}