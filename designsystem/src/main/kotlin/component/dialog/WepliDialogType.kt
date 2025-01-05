package component.dialog

sealed class WepliDialogType {
    data class TwoButton(
        val okButtonText: String,
        val cancelButtonText: String,
        val okButtonClick: () -> Unit,
        val cancelButtonClick: () -> Unit,
    ) : WepliDialogType()

    data class OneButton(
        val okButtonText: String,
        val okButtonClick: () -> Unit,
    ) : WepliDialogType()
}