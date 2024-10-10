package com.wepli.community.navigator

import android.app.Activity
import android.content.Intent
import com.wepli.community.main.CommunityActivity
import com.wepli.navigator.feature.community.CommunityNavigator
import common.startActivityWithAnimation
import javax.inject.Inject

internal class CommunityNavigatorImpl @Inject constructor() : CommunityNavigator {

    override fun navigateFrom(activity: Activity, intentBuilder: Intent.() -> Intent, withFinish: Boolean) {
        activity.startActivityWithAnimation<CommunityActivity>(intentBuilder, withFinish)
    }
}