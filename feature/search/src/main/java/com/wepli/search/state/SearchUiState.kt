package com.wepli.search.state

import base.Intent
import base.SideEffect
import base.UiState

data class SearchUiState(
    val searchInput: String = "",
) : UiState

interface SearchEffect : SideEffect

interface SearchIntent : Intent {
    data class OnSearchQueryChanged(val query: String) : SearchIntent
}