package com.wepli.data.applemusic

import com.wepli.core.kotlin.FlowResult
import retrofit2.http.GET
import retrofit2.http.Query
import wepli.domain.search.AppleSearchResponse

interface AppleMusicApi {

    @GET("v1/catalog/kr/search")
    fun searchForCatalogResources(
        @Query("term") term: String, // 검색어
        @Query("limit") limit: Int = 25, // 반환 결과 수
        @Query("offset") offset: Int? = null, // 검색 결과의 시작 위치
        @Query("types") types: List<String> // 검색 결과의 타입 [activities, albums, apple-curators, artists, curators, music-videos, playlists, record-labels, songs, stations]
    ): FlowResult<AppleSearchResponse>
}