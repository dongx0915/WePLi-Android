package com.wepli.feature.namecard.detail.viewmodel

import base.BaseMviViewModel
import com.wepli.feature.namecard.detail.mvi.NameCardDetailEffect
import com.wepli.feature.namecard.detail.mvi.NameCardDetailIntent
import com.wepli.feature.namecard.detail.mvi.NameCardDetailUiState
import com.wepli.shared.feature.uimodel.namecard.PhotoCardUiData
import com.wepli.uimodel.music.SongUiData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import repository.user.UserRepository
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class NameCardDetailViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseMviViewModel<NameCardDetailUiState, NameCardDetailEffect, NameCardDetailIntent>(
    initialState = NameCardDetailUiState()
) {
    override fun processIntent(intent: NameCardDetailIntent) {
        when(intent) {
            is NameCardDetailIntent.Initialize -> handleInitialize(intent.totalPage, intent.oneLineIntroMaxLength)
            is NameCardDetailIntent.OnChangedOneLineIntro -> handleChangedOneLineIntro(intent.text)
            is NameCardDetailIntent.OnChangedInstagramId -> handleChangedInstagramId(intent.text)
            is NameCardDetailIntent.OnFavoriteSongSelected -> handleFavoriteSongSelected(intent.song)
            is NameCardDetailIntent.OnNextPage -> handleNextPage()
            is NameCardDetailIntent.OnPreviousPage -> handlePreviousPage()
        }
    }

    private fun handleInitialize(totalPage: Int, oneLineIntroMaxLength: Int) {
        updateState {
            copy(
                totalPage = totalPage,
                oneLineIntro = NameCardDetailUiState.FieldState(maxLength = oneLineIntroMaxLength)
            )
        }
    }

    private fun handleChangedOneLineIntro(text: String) {
        updateState { copy(oneLineIntro = oneLineIntro.copy(text = text)) }
    }

    private fun handleChangedInstagramId(text: String) {
        updateState { copy(instagramId = instagramId.copy(text = text)) }
    }

    private fun handleFavoriteSongSelected(song: SongUiData) {
        updateState { copy(selectedFavoriteSong = song) }
    }

    private fun handleNextPage() = intent {
        if (state.isLastPage) {
            makeNameCard()
        } else {
            updateState { setNextPage() }
        }
    }

    private fun handlePreviousPage() = intent {
        if (state.currentPage <= 0) {
            postSideEffect(NameCardDetailEffect.NavigateBack)
        } else {
            reduce { state.setPreviousPage() }
        }
    }

    private fun makeNameCard() = intent {
        reduce { state.copy(isLoading = true) }

        withContext(Dispatchers.Default) {
            val startTime = System.currentTimeMillis()
            while (state.makeCardProgress < 1.0f) {
                val elapsedTime = System.currentTimeMillis() - startTime

                reduce {
                    // 3초 이후에는 최소 증가량을 높여 더 빠르게 진행
                    val minIncrement = if (elapsedTime >= 3000L) 0.15 else 0.05
                    val randomIncrement = Random.nextDouble(minIncrement, 0.25).toFloat()
                    val progress = (state.makeCardProgress + randomIncrement)

                    state.updateProgress(progress)
                }

                // 3초 이후에는 딜레이를 줄여서 빠르게 마무리
                val delayTime = if (elapsedTime >= 3000L) {
                    Random.nextLong(100L, 200L) // 3초 이후에는 짧은 딜레이 적용
                } else {
                    Random.nextLong(250L, 750L) // 3초 이전까지는 기존 딜레이 유지
                }

                delay(delayTime)
            }
        }

        val nameCardResult = makeNameCardResult(state)
        postSideEffect { NameCardDetailEffect.OnCompleteChapter(nameCardResult) }
    }

    private suspend fun makeNameCardResult(
        state: NameCardDetailUiState
    ) = withContext(Dispatchers.IO) {
        val user = userRepository.getUser()
        PhotoCardUiData(
            nickname = user?.nickname ?: "",
            profileImg = user?.profileImgUrl ?: "",
            userTendency = "Melody Memories",
            oneLineIntro = state.oneLineIntro.text,
            instagramId = state.instagramId.text,
            favoriteSong = state.selectedFavoriteSong ?: SongUiData()
        )
    }
}