package extensions

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowInsetsControllerCompat

fun Activity.setStatusBarColor(color: Color, darkIcons: Boolean = true) {
    // 상태바의 배경색을 설정
    window.statusBarColor = color.toArgb()
    // 상태바 아이콘 색상을 설정 (어두운 아이콘/밝은 아이콘)
    WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = darkIcons
}

inline fun <reified T : Parcelable> Intent.getParcelableExtraCompat(key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableExtra(key, T::class.java)
    } else {
        getParcelableExtra(key)
    }
}
