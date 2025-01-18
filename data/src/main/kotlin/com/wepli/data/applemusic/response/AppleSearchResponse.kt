package com.wepli.data.applemusic.response

import com.wepli.data.applemusic.common.response.AppleAlbumResponse
import com.wepli.data.applemusic.common.response.AppleArtistResponse
import com.wepli.data.applemusic.common.response.AppleSongResponse
import com.wepli.data.applemusic.common.response.base.AppleSearchBaseResponse
import com.wepli.data.applemusic.common.response.toEntity
import kotlinx.serialization.Serializable
import model.album.Album
import model.artist.AppleArtist
import model.music.Song

/**
 * https://api.music.apple.com/v1/catalog/us/search?types=songs,albums,artists&term=beach+bunny
 * - type에 검색할 타입을 넣어주면 됨
 */
@Serializable
data class AppleSearchResponse(
    val results: Result? = null,
) {
    @Serializable
    data class Result(
        val songs: AppleSearchBaseResponse<AppleSongResponse>? = null,
        val artists: AppleSearchBaseResponse<AppleArtistResponse>? = null,
        val albums: AppleSearchBaseResponse<AppleAlbumResponse>? = null,
    )
}

fun AppleSearchResponse.toMusicSearchResult(): List<Song> {
    return results?.songs?.data?.map { it.toEntity() }.orEmpty()
}

fun AppleSearchResponse.toArtistSearchResult(): List<AppleArtist> {
    return results?.artists?.data?.map { it.toEntity() }.orEmpty()
}

fun AppleSearchResponse.toAlbumSearchResult(): List<Album> {
    return results?.albums?.data?.map { it.toEntity() }.orEmpty()
}