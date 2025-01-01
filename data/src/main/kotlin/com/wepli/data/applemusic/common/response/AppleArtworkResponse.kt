package com.wepli.data.applemusic.common.response

import kotlinx.serialization.Serializable

/**
 * 이미지에 대한 정보를 담고있는 객체
 * - ex) 앨범 커버, 가수 이미지 등
 *
 * @property bgColor 이미지의 평균 배경색 (주요 색상)
 * @property width 이미지 너비
 * @property height 이미지 높이
 * @property textColor1 배경색이 표시되는 경우 사용되는 기본 텍스트 색상
 * @property textColor2 배경색이 표시되는 경우 사용되는 보조 텍스트 색상
 * @property textColor3 배경색이 표시되는 경우 사용되는 세 번째 텍스트 색상
 * @property textColor4 배경색이 표시되는 경우 최종 텍스트
 * @property url 이미지 url ({w}x{h} 부분에 width, height 대입)
 */
@Serializable
data class AppleArtworkResponse(
    val bgColor: String? = null,
    val width: Int? = null,
    val height: Int? = null,
    val textColor1: String? = null,
    val textColor2: String? = null,
    val textColor3: String? = null,
    val textColor4: String? = null,
    val url: String? = null,
)