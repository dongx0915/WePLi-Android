package com.wepli.core.kotlin.date

import org.joda.time.DateTime

fun DateTime.toCommonFormat(): String {
    return this.toString("yyyy.MM.dd HH:mm")
}