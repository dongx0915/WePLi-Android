package com.wepli.app.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.wepli.app.ChartScreen
import com.wepli.community.navigation.communityDetailGraph
import com.wepli.community.navigation.communityMainGraph
import com.wepli.community.navigation.navigateToCommunityDetail
import com.wepli.home.navigation.homeGraph
import com.wepli.mypage.menus.appinfo.navigation.mypageAppInfoGraph
import com.wepli.mypage.menus.appinfo.navigation.navigateToAppInfo
import com.wepli.mypage.menus.mypage.navigation.mypageMainGraph
import com.wepli.app.navigation.extensions.navigateToBack
import com.wepli.community.navigation.communityWriteGraph
import com.wepli.community.navigation.navigateToBackAndPostRefresh
import com.wepli.community.navigation.navigateToCommunityWrite
import com.wepli.feature.namecard.detail.navigation.photoCardDetailGraph
import com.wepli.feature.namecard.detail.navigation.navigateToPhotoCardDetail
import com.wepli.feature.namecard.main.navigation.photoCardMainGraph
import com.wepli.feature.namecard.main.navigation.navigateToPhotoCardMain
import com.wepli.feature.namecard.result.navigation.nameCardResultGraph
import com.wepli.feature.namecard.result.navigation.navigateToNameCardResult
import com.wepli.playlist.navigation.navigateToPlaylistDetail
import com.wepli.playlist.navigation.playlistDetailGraph
import com.wepli.search.navigation.SearchScreenMode
import com.wepli.search.navigation.navigateBackWithSelectedSongs
import com.wepli.search.navigation.navigateToSearchDetail
import com.wepli.search.navigation.searchDetailGraph
import com.wepli.search.navigation.searchMainGraph

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    startDestination: String,
    goToLoginActivity: () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = {
            fadeIn(animationSpec = tween(500))
        },
        exitTransition = {
            fadeOut(animationSpec = tween(500))
        }
    ) {
        // 홈 Graph
        homeGraph(
            navOnPlaylistDetail = { playlistId -> navController.navigateToPlaylistDetail(playlistId) }
        )

        // 검색 Graph
        searchGraph(navController)

        composable(BottomNavRoute.Chart.route) {
            ChartScreen()
        }

        // 커뮤니티 Graph
        communityGraph(navController)

        // 플레이리스트 Graph
        playlistGraph(navController)

        // 마이페이지 Graph
        mypageGraph(navController, goToLoginActivity)

        // 명함 Graph
        photoCardGraph(navController)
    }
}

// 검색 Graph
fun NavGraphBuilder.searchGraph(navController: NavController) {
    searchMainGraph { searchQuery ->
        navController.navigateToSearchDetail(
            screenMode = SearchScreenMode.Normal,
            searchQuery = searchQuery
        )
    }

    searchDetailGraph(
        navOnBack = { navController.navigateToBack() },
        navigateBackWithSelectedSongs = { selectedSongs ->
            navController.navigateBackWithSelectedSongs(selectedSongs)
        }
    )
}

// 커뮤니티 Graph
fun NavGraphBuilder.communityGraph(navController: NavHostController) {
    communityMainGraph(
        navOnCommunityDetail = { post -> navController.navigateToCommunityDetail(post) },
        navOnCommunityWrite = { navController.navigateToCommunityWrite() }
    )
    communityDetailGraph(
        navOnBack = { navController.navigateToBack() }
    )
    communityWriteGraph(
        navOnBack = { navController.navigateToBack() },
        navOnBackAndPostRefresh = { navController.navigateToBackAndPostRefresh() },
        navOnSearchDetail = {
            navController.navigateToSearchDetail(
                screenMode = SearchScreenMode.Selectable(),
                searchQuery = ""
            )
        }
    )
}

// 플레이리스트 Graph
fun NavGraphBuilder.playlistGraph(navController: NavHostController) {
    playlistDetailGraph(
        navOnBack = { navController.navigateToBack() }
    )
}

// 마이페이지 Graph
fun NavGraphBuilder.mypageGraph(
    navController: NavHostController,
    goToLoginActivity: () -> Unit,
) {
    mypageMainGraph(
        navOnAppInfo = { navController.navigateToAppInfo() },
        navOnNameCard = { navController.navigateToPhotoCardMain() },
        goToLoginActivity = { goToLoginActivity() }
    )
    mypageAppInfoGraph(
        navOnBack = { navController.navigateToBack() }
    )
}

fun NavGraphBuilder.photoCardGraph(navController: NavController) {
    photoCardMainGraph(
        navOnBack = { navController.popBackStack() },
        navOnPhotoCardDetail = { navController.navigateToPhotoCardDetail() }
    )
    photoCardDetailGraph(
        navOnBack = { navController.popBackStack() },
        navOnSongSearchScreen = {
            navController.navigateToSearchDetail(
                screenMode = SearchScreenMode.Selectable(1),
                searchQuery = ""
            )
        },
        navOnNameCardResultScreen = {
            navController.navigateToNameCardResult(it)
        }
    )
    nameCardResultGraph(
        navOnBack = { navController.popBackStack() }
    )
}