package repository.artist

import com.wepli.kotlin.FlowResult
import model.artist.Artist

interface ArtistRepository {

    suspend fun getArtists(): FlowResult<List<Artist>>
}