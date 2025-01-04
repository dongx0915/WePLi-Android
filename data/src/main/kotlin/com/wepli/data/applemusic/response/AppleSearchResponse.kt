package wepli.domain.search

import com.wepli.data.applemusic.common.response.AppleAlbumResponse
import com.wepli.data.applemusic.common.response.AppleArtistResponse
import com.wepli.data.applemusic.common.response.AppleSongResponse
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
        val songs: SongsSearchResult? = null,
        val artists: ArtistsSearchResult? = null,
        val albums: AlbumSearchResult? = null,
    ) {
        /**
         * @property data
         * @property href
         * @property next 다음 페이지를 가져오기 위한 커서(url) (더 많은 항목이 있는 경우)
         */
        @Serializable
        data class SongsSearchResult(
            val href: String? = null,
            val next: String? = null,
            val data: List<AppleSongResponse>? = null,
        )

        /**
         * @property data
         * @property href
         * @property next 다음 페이지를 가져오기 위한 커서(url) (더 많은 항목이 있는 경우)
         */
        @Serializable
        data class ArtistsSearchResult(
            val href: String? = null,
            val next: String? = null,
            val data: List<AppleArtistResponse>? = null,
        )

        /**
         * @property data
         * @property href
         * @property next 다음 페이지를 가져오기 위한 커서(url) (더 많은 항목이 있는 경우)
         */
        @Serializable
        data class AlbumSearchResult(
            val href: String? = null,
            val next: String? = null,
            val data: List<AppleAlbumResponse>? = null,
        )
    }
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