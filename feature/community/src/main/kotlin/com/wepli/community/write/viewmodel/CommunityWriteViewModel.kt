package com.wepli.community.write.viewmodel

import android.util.Log
import base.BaseMviViewModel
import com.wepli.community.write.mvi.CommunityWriteEffect
import com.wepli.community.write.mvi.CommunityWriteIntent
import com.wepli.community.write.mvi.CommunityWriteUiState
import com.wepli.core.kotlin.suspendCollectResult
import com.wepli.uimodel.music.SongUiData
import dagger.hilt.android.lifecycle.HiltViewModel
import model.community.Post
import model.music.Song
import repository.post.PostRepository
import repository.user.UserRepository
import javax.inject.Inject

@HiltViewModel
class CommunityWriteViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository,
) : BaseMviViewModel<CommunityWriteUiState, CommunityWriteEffect, CommunityWriteIntent>(
    initialState = CommunityWriteUiState()
) {

    override fun processIntent(intent: CommunityWriteIntent) {
        when (intent) {
            is CommunityWriteIntent.UpdateTitle -> {
                handleUpdateTitle(intent.title, intent.maxLength)
            }
            is CommunityWriteIntent.UpdateContents -> {
                handleUpdateContents(intent.contents, intent.maxLength)
            }
            is CommunityWriteIntent.ShowMusicSelectBottomSheet -> {
                handleShowMusicSelectBottomSheet(intent.isVisible)
            }
            is CommunityWriteIntent.UpdateSelectedSongs -> {
                handleUpdateSelectedSongs(intent.selectedSongs)
            }
            is CommunityWriteIntent.RemoveSelectedSongs -> {
                handleRemoveSelectedSongs(intent.song)
            }
            is CommunityWriteIntent.AddPost -> {
                handleAddPost()
            }
        }
    }

    private fun handleAddPost() = intent {
        val user = userRepository.getUser() ?: return@intent

        Post(
            title = state.title.text,
            content = state.contents.text,
            author = user,
            songList = state.selectedSongs.map {
                Song(
                    id = it.id,
                    title = it.title,
                    artistName = it.artistName,
                    albumName = it.albumName,
                    coverImg = it.coverImg,
                    href = it.href,
                    durationMillis = it.durationMillis,
                    genres = emptyList()
                )
            },
        ).let {
            postRepository.addPost(it)
        }.suspendCollectResult(
            onSuccess = {
                postSideEffect(CommunityWriteEffect.SuccessAddPost)
            },
            onFailure = {
                postSideEffect(CommunityWriteEffect.FailedAddPost)
            }
        )
    }

    private fun handleUpdateTitle(title: String, maxLength: Int) {
        val updatedFiledState = CommunityWriteUiState.FieldState(
            text = title,
            isLengthExceeded = title.length > maxLength
        )
        updateState { copy(title = updatedFiledState) }
    }

    private fun handleUpdateContents(contents: String, maxLength: Int) {
        val updatedFiledState = CommunityWriteUiState.FieldState(
            text = contents,
            isLengthExceeded = contents.length > maxLength
        )

        updateState { copy(contents = updatedFiledState) }
    }

    private fun handleShowMusicSelectBottomSheet(isVisible: Boolean) {
        updateState { copy(isShowMusicSelectBottomSheet = isVisible) }
    }

    private fun handleUpdateSelectedSongs(newSelectedSongs: List<SongUiData>) {
        updateState {
            val updatedSelectSongs: List<SongUiData> = (selectedSongs + newSelectedSongs).distinct()
            copy(selectedSongs = updatedSelectSongs)
        }
    }

    private fun handleRemoveSelectedSongs(song: SongUiData) {
        updateState { copy(selectedSongs = selectedSongs - song) }
    }
}