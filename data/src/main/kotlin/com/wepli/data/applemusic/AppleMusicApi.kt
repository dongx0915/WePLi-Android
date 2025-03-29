package com.wepli.data.applemusic

import com.wepli.core.kotlin.flow.FlowResult
import com.wepli.data.applemusic.common.response.AppleAlbumResponse
import com.wepli.data.applemusic.common.response.AppleSongResponse
import com.wepli.data.applemusic.common.response.base.AppleDataWrapper
import com.wepli.data.applemusic.response.AppleCatalogResponse
import retrofit2.http.GET
import retrofit2.http.Query
import com.wepli.data.applemusic.response.AppleSearchResponse
import retrofit2.http.Path

interface AppleMusicApi {

    // 검색
    @GET("v1/catalog/kr/search")
    fun searchForCatalogResources(
        @Query("term") term: String, // 검색어
        @Query("limit") limit: Int = 25, // 반환 결과 수
        @Query("offset") offset: Int? = null, // 검색 결과의 시작 위치
        @Query("types") types: List<String> // 검색 결과의 타입 [activities, albums, apple-curators, artists, curators, music-videos, playlists, record-labels, songs, stations]
    ): FlowResult<AppleSearchResponse>

    // 인기 차트 조회
    @GET("v1/catalog/kr/charts")
    fun searchForCatalogCharts(
        @Query("limit") limit: Int = 10, // 반환 결과 수
        @Query("offset") offset: Int? = null, // 검색 결과의 시작 위치
        @Query("types") types: List<String>, // 차트 타입 [albums, songs, playlists]
    ): FlowResult<AppleCatalogResponse>

    // 노래
    @GET("v1/catalog/kr/songs/{songId}")
    fun getCatalogSong(@Path("songId") songId: String): FlowResult<AppleDataWrapper<AppleSongResponse>>

    // 앨범
    @GET("v1/catalog/kr/albums/{albumId}")
    fun getCatalogAlbum(@Path("albumId") albumId: String): FlowResult<AppleDataWrapper<AppleAlbumResponse>>
}