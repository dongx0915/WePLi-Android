package com.wepli.data.artist.datasource.remote

import com.wepli.data.artist.response.ArtistListResponse
import com.wepli.core.kotlin.FlowResult

interface ArtistDataSource {

    suspend fun getArtists(): FlowResult<ArtistListResponse>
}