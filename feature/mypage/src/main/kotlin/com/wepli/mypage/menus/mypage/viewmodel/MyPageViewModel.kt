package com.wepli.mypage.menus.mypage.viewmodel

import base.BaseMviViewModel
import base.Intent
import base.SideEffect
import base.UiState
import com.wepli.shared.feature.uimodel.user.UserUiData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.user.User
import repository.user.UserRepository
import javax.inject.Inject

data class MyPageUiState(
    val user: UserUiData,
    val showLogoutPopup: Boolean = false
) : UiState

interface MyPageEffect : SideEffect {
    data object SuccessLogout : MyPageEffect
}

interface MyPageIntent : Intent {
    data class ShowLogoutPopup(val isShow: Boolean) : MyPageIntent
    data object RequestLogout : MyPageIntent
}

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : BaseMviViewModel<MyPageUiState, MyPageEffect, MyPageIntent>(
    initialState = MyPageUiState(user = UserUiData())
) {

    init {
        loadUser()
    }

    override fun processIntent(intent: MyPageIntent) {
        when (intent) {
            is MyPageIntent.ShowLogoutPopup -> handleOnClickLogout(intent.isShow)
            MyPageIntent.RequestLogout -> handleRequestLogout()
        }
    }

    private fun loadUser() = intent {
        launchWithHandler {
            val user: User? = withContext(Dispatchers.IO) { userRepository.getUser() }
            user?.let {
                reduce { state.copy(user = UserUiData.fromDomain(it)) }
            }
        }
    }

    private fun handleOnClickLogout(isShow: Boolean) = intent {
        reduce { state.copy(showLogoutPopup = isShow) }
    }

    private fun handleRequestLogout() = intent {
        withContext(Dispatchers.IO) {
            userRepository.clearUserData()
        }

        postSideEffect(MyPageEffect.SuccessLogout)
    }
}