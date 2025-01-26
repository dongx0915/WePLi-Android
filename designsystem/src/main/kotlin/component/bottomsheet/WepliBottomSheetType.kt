package component.bottomsheet

sealed interface WepliBottomSheetType {
    val title: String

    data class Normal(
        override val title: String
    ) : WepliBottomSheetType
    data class Button(
        override val title: String,
        val text: String,
        val onClick: () -> Unit
    ) : WepliBottomSheetType
}