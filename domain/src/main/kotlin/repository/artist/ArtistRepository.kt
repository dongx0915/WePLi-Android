package repository.artist

import com.wepli.core.kotlin.flow.FlowResult
import model.artist.Artist

interface ArtistRepository {

    suspend fun getArtists(): FlowResult<List<Artist>>
}