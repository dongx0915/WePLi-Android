package com.wepli.data.artist.datasource.remote

import com.wepli.data.artist.response.ArtistListResponse

interface ArtistDataSource {

    suspend fun getArtists(): ArtistListResponse
}