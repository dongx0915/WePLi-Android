package com.wepli.uimodel.artist

import com.wepli.common.UiModel
import com.wepli.common.UiModelMapper
import kotlinx.parcelize.Parcelize
import model.artist.Artist

/**
 * 가수 정보
 * @property name 가수 이름
 * @property profileUrl 가수 프로필 URL
 */
@Parcelize
data class ArtistUiData(
    val name: String,
    val profileUrl: String,
) : UiModel