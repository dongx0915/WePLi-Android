package com.wepli.data.artist.datasource.remote

import com.wepli.data.artist.ArtistApi
import com.wepli.data.artist.response.ArtistListResponse
import javax.inject.Inject

class ArtistDataSourceImpl @Inject constructor(
    private val artistApi: ArtistApi
) : ArtistDataSource {

    override suspend fun getArtists(): ArtistListResponse {
        return artistApi.getArtists()
    }
}