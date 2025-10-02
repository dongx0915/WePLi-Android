package com.wepli.feature.devmode.main.utils

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.firebase.messaging.FirebaseMessaging
import com.wepli.feature.devmode.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.system.exitProcess

object DevModeUtil {

    fun getSdkVersion(): Int = Build.VERSION.SDK_INT

    fun getAndroidOS(): String = Build.VERSION.RELEASE

    fun getDeviceModel(): String = "${Build.BRAND} ${Build.MODEL}"

    fun getDeviceResourceBucket(metrics: DisplayMetrics?): String {
        val densityDpi = metrics?.densityDpi ?: return "unknown"

        return when {
            densityDpi <= DisplayMetrics.DENSITY_LOW -> "ldpi"
            densityDpi <= DisplayMetrics.DENSITY_MEDIUM -> "mdpi"
            densityDpi <= DisplayMetrics.DENSITY_HIGH -> "hdpi"
            densityDpi <= DisplayMetrics.DENSITY_XHIGH -> "xhdpi"
            densityDpi <= DisplayMetrics.DENSITY_XXHIGH -> "xxhdpi"
            densityDpi <= DisplayMetrics.DENSITY_XXXHIGH -> "xxxhdpi"
            else -> "unknown"
        }
    }

    fun getDpi(metrics: DisplayMetrics?): Int = metrics?.densityDpi ?: 0

    fun getDeviceWidth(activity: Activity): Int {
        val metrics = activity.resources.displayMetrics

        return metrics.widthPixels
    }

    fun getDeviceHeight(activity: Activity): Int {
        val metrics = activity.resources.displayMetrics
        val windowManager = activity.windowManager

        return metrics.heightPixels + getStatusBarHeight(windowManager) + getNaviBarHeight(windowManager)
    }

    fun getStatusBarHeight(windowManager: WindowManager): Int {
        val windowMetrics = windowManager.currentWindowMetrics
        val insets = windowMetrics.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.statusBars())

        return insets.top
    }

    fun getNaviBarHeight(windowManager: WindowManager): Int {
        val windowMetrics = windowManager.currentWindowMetrics
        val insets = windowMetrics.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.navigationBars())

        return insets.bottom
    }

    suspend fun getFcmToken(): String {
        return suspendCancellableCoroutine {
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (!task.isSuccessful) return@addOnCompleteListener

                val result = if (task.result != null) {
                    Result.success(task.result)
                } else {
                    Result.failure(Exception("FCM token is null"))
                }

                it.resumeWith(result = result)
            }
        }
    }

    fun restartApplication(activity: ComponentActivity) = with(activity) {
        val packageName = packageManager.getLaunchIntentForPackage(activity.packageName)
        val component = packageName?.component

        activity.lifecycleScope.launch {
            val restartDesc = ContextCompat.getString(activity, R.string.dev_mode_require_restart)
            Toast.makeText(this@with, restartDesc, Toast.LENGTH_LONG).show()
            delay(2000)

            Intent.makeRestartActivityTask(component).apply {
                startActivity(this)
                exitProcess(0)
            }
        }
    }
}