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
    val user: UserUiData
) : UiState

interface MyPageEffect : SideEffect

interface MyPageIntent : Intent


@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : BaseMviViewModel<MyPageUiState, MyPageEffect, MyPageIntent>(
    initialState = MyPageUiState(user = UserUiData())
){

    override fun processIntent(intent: MyPageIntent) {
        //
    }

    init {
        loadUser()
    }

    private fun loadUser() = intent {
        launchWithHandler {
            val user: User? = withContext(Dispatchers.IO) { userRepository.getUser() }
            user?.let {
                reduce { state.copy(user = UserUiData.fromDomain(it)) }
            }
        }
    }
}