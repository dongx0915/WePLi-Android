package com.wepli.data.applemusic.repository

import com.wepli.core.kotlin.FlowResult
import com.wepli.data.applemusic.datasource.AppleMusicDataSource
import com.wepli.data.network.toEntityResult
import model.album.Album
import model.artist.AppleArtist
import model.music.Song
import repository.applemusic.AppleMusicRepository
import wepli.domain.search.toAlbumSearchResult
import wepli.domain.search.toArtistSearchResult
import wepli.domain.search.toMusicSearchResult
import javax.inject.Inject

class AppleMusicRepositoryImpl @Inject constructor(
    private val appleMusicDataSource: AppleMusicDataSource
) : AppleMusicRepository {

    override fun searchMusics(query: String): FlowResult<List<Song>> {
        return appleMusicDataSource.searchForCatalogResources(
            query = query,
            searchTypes = listOf("songs")
        ).toEntityResult {
            it.toMusicSearchResult()
        }
    }

    override fun searchAlbums(query: String): FlowResult<List<Album>> {
        return appleMusicDataSource.searchForCatalogResources(
            query = query,
            searchTypes = listOf("albums")
        ).toEntityResult {
            it.toAlbumSearchResult()
        }
    }

    override fun searchArtists(query: String): FlowResult<List<AppleArtist>> {
        return appleMusicDataSource.searchForCatalogResources(
            query = query,
            searchTypes = listOf("artists")
        ).toEntityResult {
            it.toArtistSearchResult()
        }
    }
}