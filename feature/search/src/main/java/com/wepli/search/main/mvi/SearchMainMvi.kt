package com.wepli.search.main.mvi

import base.Intent
import base.SideEffect
import base.UiState
import model.music.Song
import model.recommend.RecommendKeyword

data class SearchMainUiState(
    val recommendKeyword: RecommendKeyword? = null,
    val hotKeywords: List<Song> = emptyList()
) : UiState

interface SearchMainEffect : SideEffect

interface SearchMainIntent : Intent