package com.wepli.core.kotlin.time

import java.util.concurrent.TimeUnit


fun Long.formatAsRemainingTime(): String {
    val days = TimeUnit.MILLISECONDS.toDays(this)
    val hours = TimeUnit.MILLISECONDS.toHours(this) % 24
    val minutes = TimeUnit.MILLISECONDS.toMinutes(this) % 60
    val seconds = TimeUnit.MILLISECONDS.toSeconds(this) % 60

    return "${days}일 ${hours}시간 ${minutes}분 ${seconds}초"
}