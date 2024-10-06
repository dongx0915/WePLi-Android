package repository.artist

import model.artist.Artist

interface ArtistRepository {

    suspend fun getArtists(): List<Artist>
}