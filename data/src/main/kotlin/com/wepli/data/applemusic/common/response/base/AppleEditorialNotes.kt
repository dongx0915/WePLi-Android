package com.wepli.data.applemusic.common.response.base

import kotlinx.serialization.Serializable

/**
 * @property short 짧은 설명
 * @property standard 표준 설명
 * @property name 편집자의 메모의 이름
 * @property tagline 편집자의 메모에 대한 태그 라인
 */
@Serializable
data class AppleEditorialNotes(
    val short: String? = null,
    val standard: String? = null,
    val name: String? = null,
    val tagline: String? = null,
)
