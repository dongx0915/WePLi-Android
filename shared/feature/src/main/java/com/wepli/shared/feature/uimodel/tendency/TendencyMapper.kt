package com.wepli.shared.feature.uimodel.tendency

import com.wepli.core.resources.R
import model.tendency.Tendency

fun Tendency.toIconResId(): Int {
    return when (this) {
        Tendency.BASIC_RHYTHM -> R.drawable.img_headphone
        Tendency.SOCIAL_TUNES -> R.drawable.img_bubbles
        Tendency.MELODY_MEMORIES -> R.drawable.img_cloud_memory
        Tendency.NOSTALGIA_SOONER -> R.drawable.img_radio
        Tendency.SENTIMENTAL_SYMPHONY -> R.drawable.img_crystal_ball
        Tendency.ENERGY_FLASH -> R.drawable.img_star_struck
    }
}