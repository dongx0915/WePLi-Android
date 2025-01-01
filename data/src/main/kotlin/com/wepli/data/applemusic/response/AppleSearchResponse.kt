package wepli.domain.search

import com.wepli.data.applemusic.common.response.AppleAlbumResponse
import com.wepli.data.applemusic.common.response.AppleArtistResponse
import com.wepli.data.applemusic.common.response.AppleSongResponse
import com.wepli.data.applemusic.common.response.toEntity
import model.album.Album
import model.artist.AppleArtist
import model.music.AppleSong

/**
 * https://api.music.apple.com/v1/catalog/us/search?types=songs,albums,artists&term=beach+bunny
 * - type에 검색할 타입을 넣어주면 됨
 */
data class AppleSearchResponse(
    val result: Result?,
) {
    data class Result(
        val songs: SongsSearchResult?,
        val artists: ArtistsSearchResult?,
        val albums: AlbumSearchResult?,
    ) {
        /**
         * @property data
         * @property href
         * @property next 다음 페이지를 가져오기 위한 커서(url) (더 많은 항목이 있는 경우)
         */
        data class SongsSearchResult(
            val href: String?,
            val next: String?,
            val data: List<AppleSongResponse>?,
        )

        /**
         * @property data
         * @property href
         * @property next 다음 페이지를 가져오기 위한 커서(url) (더 많은 항목이 있는 경우)
         */
        data class ArtistsSearchResult(
            val href: String?,
            val next: String?,
            val data: List<AppleArtistResponse>?,
        )

        /**
         * @property data
         * @property href
         * @property next 다음 페이지를 가져오기 위한 커서(url) (더 많은 항목이 있는 경우)
         */
        data class AlbumSearchResult(
            val href: String?,
            val next: String?,
            val data: List<AppleAlbumResponse>?,
        )
    }
}

fun AppleSearchResponse.toMusicSearchResult(): List<AppleSong> {
    return result?.songs?.data?.map { it.toEntity() }.orEmpty()
}

fun AppleSearchResponse.toArtistSearchResult(): List<AppleArtist> {
    return result?.artists?.data?.map { it.toEntity() }.orEmpty()
}

fun AppleSearchResponse.toAlbumSearchResult(): List<Album> {
    return result?.albums?.data?.map { it.toEntity() }.orEmpty()
}