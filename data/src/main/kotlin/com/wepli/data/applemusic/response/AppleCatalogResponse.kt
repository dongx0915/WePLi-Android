package com.wepli.data.applemusic.response

import com.wepli.data.applemusic.common.response.AppleAlbumResponse
import com.wepli.data.applemusic.common.response.AppleArtistResponse
import com.wepli.data.applemusic.common.response.AppleSongResponse
import com.wepli.data.applemusic.common.response.base.AppleCatalogBaseResponse
import com.wepli.data.applemusic.common.response.base.AppleSearchBaseResponse
import com.wepli.data.applemusic.common.response.toEntity
import kotlinx.serialization.Serializable
import model.album.Album
import model.artist.AppleArtist
import model.music.Song

@Serializable
data class AppleCatalogResponse(
    val results: Result? = null,
) {
    @Serializable
    data class Result(
        val songs: List<AppleCatalogBaseResponse<AppleSongResponse>>? = null,
        val artists: List<AppleCatalogBaseResponse<AppleArtistResponse>>? = null,
        val albums: List<AppleCatalogBaseResponse<AppleAlbumResponse>>? = null,
    )
}

fun AppleCatalogResponse.toSongList(): List<Song> {
    return results?.songs?.first()?.data?.map { it.toEntity() }.orEmpty()
}