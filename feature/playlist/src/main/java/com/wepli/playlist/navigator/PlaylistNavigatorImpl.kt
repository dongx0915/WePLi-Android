package com.wepli.playlist.navigator

import android.app.Activity
import android.content.Intent
import com.wepli.navigator.feature.playlist.PlaylistNavigator
import com.wepli.playlist.PlaylistActivity
import common.startActivityWithAnimation
import javax.inject.Inject

internal class PlaylistNavigatorImpl @Inject constructor() : PlaylistNavigator {

    override fun navigateFrom(activity: Activity, intentBuilder: Intent.() -> Intent, withFinish: Boolean) {
        activity.startActivityWithAnimation<PlaylistActivity>(intentBuilder, withFinish)
    }
}