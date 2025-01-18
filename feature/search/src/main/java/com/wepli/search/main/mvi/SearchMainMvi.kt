package com.wepli.search.main.mvi

import base.Intent
import base.SideEffect
import base.UiState
import model.recommend.RecommendKeyword

data class SearchMainUiState(
    val recommendKeyword: RecommendKeyword? = null,
    val hotKeywords: List<String> = emptyList()
) : UiState

interface SearchMainEffect : SideEffect

interface SearchMainIntent : Intent