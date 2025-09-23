package com.wepli.mypage.menus.mypage.viewmodel

import androidx.lifecycle.viewModelScope
import base.BaseMviViewModel
import base.Intent
import base.SideEffect
import base.UiState
import com.wepli.core.common.BuildConfig
import template.menu.MenuSection
import com.wepli.shared.feature.uimodel.user.UserUiData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import repository.user.UserRepository
import javax.inject.Inject

data class MyPageUiState(
    val user: UserUiData,
    val showLogoutPopup: Boolean = false,
    val menuSections: List<MenuSection> = emptyList()
) : UiState

sealed interface MyPageEffect : SideEffect {
    data object SuccessLogout : MyPageEffect
    data object FailedLogout : MyPageEffect
    data object NavigateOnAppInfo : MyPageEffect
    data object NavigateOnPhotoCard : MyPageEffect
    data object NavigateOnDevMode : MyPageEffect
}

sealed interface MyPageIntent : Intent {
    data object None : MyPageIntent
    data class ShowLogoutPopup(val isShow: Boolean) : MyPageIntent
    data object RequestLogout : MyPageIntent
    data object NavigateOnAppInfo : MyPageIntent
    data object NavigateDevMode : MyPageIntent
    data object OnClickPhotoCardMenu : MyPageIntent
}

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val supabase: SupabaseClient,
    private val userRepository: UserRepository,
) : BaseMviViewModel<MyPageUiState, MyPageEffect, MyPageIntent>(
    initialState = MyPageUiState(user = UserUiData())
) {

    init {
        collectUserFlow()
        setMenuSections()
    }

    private fun setMenuSections() = intent {
        val menuSections = listOf(
            MenuSection(
                title = "내 활동",
                items = listOf(
                    MenuSection.MenuItem("내 플레이리스트", MyPageIntent.None),
                    MenuSection.MenuItem("참여한 릴레이리스트", MyPageIntent.None),
                    MenuSection.MenuItem("좋아요 • 저장", MyPageIntent.None),
                    MenuSection.MenuItem("포토카드 만들기", MyPageIntent.OnClickPhotoCardMenu),
                )
            ),
            MenuSection(
                title = "설정",
                items = listOf(
                    MenuSection.MenuItem("알림 설정", MyPageIntent.None),
                )
            ),
            MenuSection(
                title = "앱 정보",
                items = listOf(
                    MenuSection.MenuItem("서비스 이용 가이드", MyPageIntent.None),
                    MenuSection.MenuItem("공지 • 이용약관", MyPageIntent.NavigateOnAppInfo),
                    MenuSection.MenuItem("앱 버전", MyPageIntent.None),
                )
            ),
            MenuSection(
                title = "기타",
                items = mutableListOf(
                    MenuSection.MenuItem("로그아웃", MyPageIntent.ShowLogoutPopup(true)),
                ).apply {
                    if (BuildConfig.DEBUG) {
                        add(MenuSection.MenuItem("개발자 모드", MyPageIntent.NavigateDevMode))
                    }
                }
            )
        )

        reduce { state.copy(menuSections = menuSections) }
    }

    override fun processIntent(intent: MyPageIntent) {
        when (intent) {
            is MyPageIntent.ShowLogoutPopup -> handleOnClickLogout(intent.isShow)
            MyPageIntent.RequestLogout -> handleRequestLogout()
            MyPageIntent.NavigateOnAppInfo -> handleNavigateOnAppInfo()
            MyPageIntent.OnClickPhotoCardMenu -> handleNavigateOnPhotoCard()
            MyPageIntent.NavigateDevMode -> handleNavigateOnDevMode()
            MyPageIntent.None -> Unit
        }
    }

    private fun collectUserFlow() = intent {
        userRepository.getUserFlow()
            .stateIn(
                scope = this@MyPageViewModel.viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = null
            )
            .collect { user ->
                user?.let {
                    updateState { copy(user = UserUiData.fromDomain(it)) }
                }
            }
    }

    private fun handleOnClickLogout(isShow: Boolean) = intent {
        reduce { state.copy(showLogoutPopup = isShow) }
    }

    private fun handleRequestLogout() = intent {
        withContext(Dispatchers.IO) {
            runCatching {
                supabase.auth.signOut()
            }.onSuccess {
                userRepository.clearUserData()
                postSideEffect(MyPageEffect.SuccessLogout)
            }.onFailure {
                postSideEffect(MyPageEffect.FailedLogout)
            }
        }
    }

    private fun handleNavigateOnAppInfo() = intent {
        postSideEffect(MyPageEffect.NavigateOnAppInfo)
    }

    private fun handleNavigateOnPhotoCard() = intent {
        postSideEffect(MyPageEffect.NavigateOnPhotoCard)
    }

    private fun handleNavigateOnDevMode() = intent {
        postSideEffect(MyPageEffect.NavigateOnDevMode)
    }
}