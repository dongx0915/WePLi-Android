package com.wepli.shared.feature.uimodel.namecard

import android.os.Parcelable
import com.wepli.uimodel.music.SongUiData
import kotlinx.parcelize.Parcelize

@Parcelize
data class NameCardUiData(
    val nickname: String = "",
    val profileImg: String = "",
    val userTendency: String = "",
    val oneLineIntro: String = "",
    val instagramId: String = "",
    val favoriteSong: SongUiData = SongUiData(),
) : Parcelable