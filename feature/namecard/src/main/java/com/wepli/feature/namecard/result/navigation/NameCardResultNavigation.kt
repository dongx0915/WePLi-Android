package com.wepli.feature.namecard.result.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.wepli.feature.namecard.result.NameCardResultScreenRoute
import com.wepli.navigator.feature.namecard.NameCardRoute
import com.wepli.shared.feature.uimodel.namecard.PhotoCardUiData
import extensions.parseFromJson
import extensions.toJsonString

// Controller
fun NavController.navigateToNameCardResult(nameCardInfo: PhotoCardUiData) {
    navigate("${NameCardRoute.RESULT.route}/${Uri.encode(nameCardInfo.toJsonString())}") {
        // Main 화면 이후 스택을 모두 제거하고 이동
        popUpTo(NameCardRoute.MAIN.route) { inclusive = true }
    }
}

// Graph
fun NavGraphBuilder.nameCardResultGraph(
    navOnBack: () -> Unit,
) {
    composable(
        route = "${NameCardRoute.RESULT.route}/{nameCardInfo}",
        arguments = listOf(
            navArgument("nameCardInfo") { type = NavType.StringType }
        )
    ) {
        val nameCardInfo = it.arguments?.getString("nameCardInfo")?.parseFromJson<PhotoCardUiData>()

        NameCardResultScreenRoute(nameCardInfo, navOnBack)
    }
}