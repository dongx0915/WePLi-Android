package com.wepli.data.applemusic.repository

import com.wepli.core.kotlin.flow.FlowResult
import com.wepli.data.applemusic.common.response.toEntity
import com.wepli.data.applemusic.datasource.AppleMusicDataSource
import com.wepli.data.network.toEntityResult
import model.album.Album
import model.artist.AppleArtist
import model.music.Song
import repository.applemusic.AppleMusicRepository
import com.wepli.data.applemusic.response.toAlbumSearchResult
import com.wepli.data.applemusic.response.toArtistSearchResult
import com.wepli.data.applemusic.response.toMusicSearchResult
import com.wepli.data.applemusic.response.toSongList
import javax.inject.Inject

class AppleMusicRepositoryImpl @Inject constructor(
    private val appleMusicDataSource: AppleMusicDataSource
) : AppleMusicRepository {

    override fun searchMusics(query: String, limit: Int): FlowResult<List<Song>> {
        return appleMusicDataSource.searchForCatalogResources(
            query = query,
            searchTypes = listOf("songs"),
            limit = limit
        ).toEntityResult {
            it.toMusicSearchResult()
        }
    }

    override fun searchAlbums(query: String, limit: Int): FlowResult<List<Album>> {
        return appleMusicDataSource.searchForCatalogResources(
            query = query,
            searchTypes = listOf("albums"),
            limit = limit,
        ).toEntityResult {
            it.toAlbumSearchResult()
        }
    }

    override fun searchArtists(query: String, limit: Int): FlowResult<List<AppleArtist>> {
        return appleMusicDataSource.searchForCatalogResources(
            query = query,
            searchTypes = listOf("artists"),
            limit = limit,
        ).toEntityResult {
            it.toArtistSearchResult()
        }
    }

    override fun getPopularSongs(): FlowResult<List<Song>> {
        return appleMusicDataSource.getCatalogCharts(
            chartTypes = listOf("songs")
        ).toEntityResult {
            it.toSongList()
        }
    }

    override fun getSongById(songId: String): FlowResult<Song> {
        return appleMusicDataSource.getCatalogSong(songId).toEntityResult {
            it.toEntity()
        }
    }

    override fun getAlbumById(albumId: String): FlowResult<Album> {
        return appleMusicDataSource.getCatalogAlbum(albumId).toEntityResult {
            it.toEntity()
        }
    }
}