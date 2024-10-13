package com.wepli.playlist

import base.BaseViewModel
import com.wepli.playlist.state.PlaylistState
import com.wepli.shared.feature.mock.artistMockData
import com.wepli.shared.feature.mock.songMockData
import com.wepli.shared.feature.uimodel.playlist.PlaylistUiData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor() : BaseViewModel() {

    private val _state: MutableStateFlow<PlaylistState> = MutableStateFlow(PlaylistState())
    val state: StateFlow<PlaylistState> = _state.asStateFlow()

    init {
        loadPlaylist()
    }

    private fun loadPlaylist() {
        _state.value = PlaylistState(
            playlist = PlaylistUiData(
                id = 0,
                title = "[Playlist] 졸릴 때 잠깨기 좋은 청량맛 노동요\uD83E\uDDCA\uD83C\uDF0A\uD83D\uDC99",
                description = "나와 비슷한 취향을 가진 사람들이 올 한해 즐겨들었던 곡들을 모아봤어요. 다른 사람들은 어떤 노래를 듣는지 궁금할 때가 있잖아요. 특...나와 비슷한 취향을 가진 사람들이 올 한해 즐겨들었던 곡들을 모아봤어요. 다른 사람들은 어떤 노래를 듣는지 궁금할 때가 있잖아요. 특...나와 비슷한 취향을 가진 사람들이 올 한해 즐겨들었던 곡들을 모아봤어요. 다른 사람들은 어떤 노래를 듣는지 궁금할 때가 있잖아요. 특...나와 비슷한 취향을 가진 사람들이 올 한해 즐겨들었던 곡들을 모아봤어요. 다른 사람들은 어떤 노래를 듣는지 궁금할 때가 있잖아요. 특...",
                coverImgUrl = "https://image.bugsm.co.kr/album/images/1000/41068/4106803.jpg",
                author = "WePLi",
                bSideTrack = songMockData,
                artists = artistMockData,
                createdAt = Date()
            )
        )
    }
}