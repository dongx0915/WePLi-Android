package com.wepli.data.artist

import com.wepli.data.artist.response.ArtistListResponse
import retrofit2.http.GET

interface ArtistApi {

    @GET("api/artists")
    suspend fun getArtists(): ArtistListResponse
}