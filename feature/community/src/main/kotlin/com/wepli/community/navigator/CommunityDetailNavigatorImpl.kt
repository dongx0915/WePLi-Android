package com.wepli.community.navigator

import android.app.Activity
import android.content.Intent
import com.wepli.community.detail.CommunityDetailActivity
import com.wepli.navigator.feature.community.CommunityDetailNavigator
import common.startActivityWithAnimation
import javax.inject.Inject

internal class CommunityDetailNavigatorImpl @Inject constructor() : CommunityDetailNavigator {

    override fun navigateFrom(activity: Activity, intentBuilder: Intent.() -> Intent, withFinish: Boolean) {
        activity.startActivityWithAnimation<CommunityDetailActivity>(intentBuilder, withFinish)
    }
}