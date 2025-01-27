package com.wepli.search.navigation

enum class SearchScreenMode {
    NORMAL,
    SELECTABLE;

    companion object {
        fun fromString(value: String): SearchScreenMode {
            return when (value.uppercase()) {
                "SELECTABLE" -> SELECTABLE
                else -> NORMAL
            }
        }
    }
}
