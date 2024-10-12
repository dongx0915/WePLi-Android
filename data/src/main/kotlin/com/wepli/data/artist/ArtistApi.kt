package com.wepli.data.artist

import com.wepli.data.artist.response.ArtistListResponse
import com.wepli.kotlin.FlowResult
import retrofit2.http.GET

interface ArtistApi {

    @GET("api/artists")
    fun getArtists(): FlowResult<ArtistListResponse>
}