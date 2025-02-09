package com.wepli.feature.namecard.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.wepli.feature.namecard.detail.navigation.nameCardDetailGraph
import com.wepli.feature.namecard.detail.navigation.navigateToNameCardDetail
import com.wepli.feature.namecard.main.navigation.nameCardMainGraph

fun NavGraphBuilder.nameCardGraph(navController: NavController) {
    nameCardMainGraph(
        navOnBack = { navController.popBackStack() },
        navOnNameCardDetail = { navController.navigateToNameCardDetail() }
    )
    nameCardDetailGraph { navController.popBackStack() }
}