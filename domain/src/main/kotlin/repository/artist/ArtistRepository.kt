package repository.artist

import common.FlowResult
import model.artist.Artist

interface ArtistRepository {

    suspend fun getArtists(): FlowResult<List<Artist>>
}