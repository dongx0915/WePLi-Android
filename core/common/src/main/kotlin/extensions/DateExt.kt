package extensions

import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.Date
import java.util.Locale

fun Date.getRelativeTime(): String {
    val createTime = LocalDateTime.ofInstant(this.toInstant(), ZoneId.systemDefault())
    val currentTime = LocalDateTime.now()
    val duration = Duration.between(createTime, currentTime)

    return when {
        duration.toMinutes() < 1 -> "방금"
        duration.toMinutes() < 60 -> "${duration.toMinutes()}분 전"
        duration.toHours() < 24 -> "${duration.toHours()}시간 전"
        duration.toDays() < 7 -> "${duration.toDays()}일 전"
        duration.toDays() < 30 -> "${duration.toDays() / 7}주 전"
        duration.toDays() < 365 -> "${duration.toDays() / 30}개월 전"
        else -> "${duration.toDays() / 365}년 전"
    }
}

fun Date.toEnglishMonthName(): String {
    val instant = Instant.ofEpochMilli(this.time)
    val localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate()
    
    return localDate.month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
}