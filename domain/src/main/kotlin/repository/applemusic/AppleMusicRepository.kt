package repository.applemusic

import com.wepli.core.kotlin.flow.FlowResult
import model.album.Album
import model.artist.AppleArtist
import model.music.Song

interface AppleMusicRepository {

    fun searchMusics(query: String, limit: Int = 25): FlowResult<List<Song>>
    fun searchAlbums(query: String, limit: Int = 25): FlowResult<List<Album>>
    fun searchArtists(query: String, limit: Int = 25): FlowResult<List<AppleArtist>>

    fun getPopularSongs(): FlowResult<List<Song>>
    fun getSongById(songId: String): FlowResult<Song>
    fun getAlbumById(albumId: String): FlowResult<Album>
}