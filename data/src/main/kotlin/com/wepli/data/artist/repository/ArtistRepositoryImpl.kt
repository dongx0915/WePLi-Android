package com.wepli.data.artist.repository

import com.wepli.data.artist.datasource.remote.ArtistDataSource
import com.wepli.data.artist.response.toDomain
import model.artist.Artist
import repository.artist.ArtistRepository
import javax.inject.Inject

class ArtistRepositoryImpl @Inject constructor(
    private val artistDataSource: ArtistDataSource
) : ArtistRepository {

    override suspend fun getArtists(): List<Artist> {
        return artistDataSource.getArtists().toDomain()
    }
}