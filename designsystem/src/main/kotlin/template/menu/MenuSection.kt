package template.menu

import base.Intent

data class MenuSection(
    val title: String,
    val items: List<MenuItem>
) {
    data class MenuItem(
        val title: String,
        val intent: Intent,
    )
}