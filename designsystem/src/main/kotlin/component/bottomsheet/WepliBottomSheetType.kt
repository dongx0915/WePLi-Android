package component.bottomsheet

sealed interface WepliBottomSheetType {
    data object Normal : WepliBottomSheetType
    data class Button(
        val text: String,
        val onClick: () -> Unit
    ) : WepliBottomSheetType
}