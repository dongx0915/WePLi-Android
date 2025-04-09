package com.wepli.search.navigation

sealed class SearchScreenMode {
    open val maxSelectCount: Int = DEFAULT_MAX_COUNT

    data object Normal : SearchScreenMode()
    data class Selectable(
        override val maxSelectCount: Int = DEFAULT_MAX_COUNT
    ) : SearchScreenMode()

    companion object {
        private const val DEFAULT_MAX_COUNT = Int.MAX_VALUE

        fun fromString(value: String, maxCount: Int = DEFAULT_MAX_COUNT): SearchScreenMode {
            return when {
                value.contains("selectable", ignoreCase = true) -> Selectable(maxCount)
                else -> Normal
            }
        }
    }
}