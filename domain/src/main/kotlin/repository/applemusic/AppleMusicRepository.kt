package repository.applemusic

import com.wepli.core.kotlin.FlowResult
import model.album.Album
import model.artist.AppleArtist
import model.music.Song

interface AppleMusicRepository {

    fun searchMusics(query: String): FlowResult<List<Song>>
    fun searchAlbums(query: String): FlowResult<List<Album>>
    fun searchArtists(query: String): FlowResult<List<AppleArtist>>
}