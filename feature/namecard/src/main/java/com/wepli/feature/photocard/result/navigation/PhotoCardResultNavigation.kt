package com.wepli.feature.photocard.result.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.wepli.feature.photocard.result.PhotoCardResultScreenRoute
import com.wepli.navigator.feature.namecard.PhotoCardRoute
import com.wepli.shared.feature.uimodel.namecard.PhotoCardUiData
import extensions.parseFromJson
import extensions.toJsonString

// Controller
fun NavController.navigateToPhotoCardResult(photoCardInfo: PhotoCardUiData) {
    navigate("${PhotoCardRoute.RESULT.route}/${Uri.encode(photoCardInfo.toJsonString())}") {
        // Main 화면 이후 스택을 모두 제거하고 이동
        popUpTo(PhotoCardRoute.MAIN.route) { inclusive = true }
    }
}

// Graph
fun NavGraphBuilder.photoCardResultGraph(
    navOnBack: () -> Unit,
) {
    composable(
        route = "${PhotoCardRoute.RESULT.route}/{photoCardInfo}",
        arguments = listOf(
            navArgument("photoCardInfo") { type = NavType.StringType }
        )
    ) {
        val photoCardInfo = it.arguments?.getString("photoCardInfo")?.parseFromJson<PhotoCardUiData>()

        PhotoCardResultScreenRoute(photoCardInfo, navOnBack)
    }
}