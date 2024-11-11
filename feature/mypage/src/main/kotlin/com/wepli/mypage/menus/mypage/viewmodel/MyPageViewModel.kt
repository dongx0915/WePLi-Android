package com.wepli.mypage.menus.mypage.viewmodel

import base.BaseViewModel
import com.wepli.mypage.menus.mypage.state.MyPageState
import com.wepli.shared.feature.mock.userMockData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor() : BaseViewModel() {

    private val _state: MutableStateFlow<MyPageState> = MutableStateFlow(MyPageState())
    val state: StateFlow<MyPageState> = _state.asStateFlow()

    init {
        loadUser()
    }

    private fun loadUser() = launchWithHandler {
        _state.value = MyPageState(
            user = userMockData.random()
        )
    }
}