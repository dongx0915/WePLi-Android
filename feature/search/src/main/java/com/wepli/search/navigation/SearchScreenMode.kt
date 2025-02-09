package com.wepli.search.navigation

sealed class SearchScreenMode {
    data object Normal : SearchScreenMode()
    data class Selectable(val maxCount: Int? = null) : SearchScreenMode()

    companion object {
        private const val DEFAULT_MAX_COUNT = Int.MAX_VALUE

        fun fromString(value: String, maxCount: Int? = null): SearchScreenMode {
            return when {
                value.contains("selectable", ignoreCase = true) -> Selectable(maxCount ?: DEFAULT_MAX_COUNT)
                else -> Normal
            }
        }
    }
}