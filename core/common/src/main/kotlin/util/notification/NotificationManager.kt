package util.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import javax.inject.Inject
import com.wepli.core.resources.R as CoreResource

class NotificationManager @Inject constructor(private val context: Context) {

    companion object {
        private const val CHANNEL_ID = "wepli_notification_channel_default"
        private const val CHANNEL_NAME = "앱 알림"
        private const val CHANNEL_DESCRIPTION = "WePLi 앱 알림"
        private const val NOTIFICATION_ID = 1001
    }

    private val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = CHANNEL_DESCRIPTION
            enableVibration(true)
            enableLights(true)
        }
        notificationManager.createNotificationChannel(channel)
    }

    fun showNotification(
        title: String,
        message: String,
        intent: Intent
    ) {
        val pendingIntent = createPendingIntent(intent)

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(CoreResource.drawable.ic_wepli_logo_white)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun createPendingIntent(intent: Intent): PendingIntent {
        val flags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE

        return PendingIntent.getActivity(context, 0, intent, flags)
    }

    fun cancelNotification() {
        notificationManager.cancel(NOTIFICATION_ID)
    }

    fun cancelAllNotifications() {
        notificationManager.cancelAll()
    }
}