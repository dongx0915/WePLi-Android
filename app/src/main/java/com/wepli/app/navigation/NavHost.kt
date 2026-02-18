package com.wepli.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import animation.transition.ScreenTransitions
import com.donglab.screennameviewer.publicapi.extensions.ScreenNameTracker
import com.wepli.app.ChartScreen
import com.wepli.community.navigation.communityDetailGraph
import com.wepli.community.navigation.communityMainGraph
import com.wepli.community.navigation.navigateToCommunityDetail
import com.wepli.home.navigation.homeGraph
import com.wepli.mypage.menus.appinfo.navigation.mypageAppInfoGraph
import com.wepli.mypage.menus.appinfo.navigation.navigateToAppInfo
import com.wepli.mypage.menus.mypage.navigation.mypageMainGraph
import com.wepli.community.navigation.communityWriteGraph
import com.wepli.community.navigation.navigateToBackAndPostRefresh
import com.wepli.community.navigation.navigateToCommunityWrite
import com.wepli.core.common.BuildConfig
import com.wepli.devmode.fcm.presentation.navigation.devModeFcmGraph
import com.wepli.devmode.fcm.presentation.navigation.navigateToDevModeFcmMain
import com.wepli.devmode.network.presentation.detail.navigation.navigateToNetworkLogDetail
import com.wepli.devmode.network.presentation.detail.navigation.networkLogDetailGraph
import com.wepli.devmode.network.presentation.main.navigation.navigateToNetworkLogMain
import com.wepli.devmode.network.presentation.main.navigation.networkLogMainGraph
import com.wepli.feature.devmode.main.navigation.devModeMainGraph
import com.wepli.feature.devmode.main.navigation.navigateToDevModeMain
import com.wepli.feature.photocard.detail.navigation.photoCardDetailGraph
import com.wepli.feature.photocard.detail.navigation.navigateToPhotoCardDetail
import com.wepli.feature.photocard.main.navigation.photoCardMainGraph
import com.wepli.feature.photocard.main.navigation.navigateToPhotoCardMain
import com.wepli.feature.photocard.result.navigation.photoCardResultGraph
import com.wepli.feature.photocard.result.navigation.navigateToPhotoCardResult
import com.wepli.feature.song.info.navigation.navigateToSongInfo
import com.wepli.feature.song.info.navigation.songInfoGraph
import com.wepli.mypage.menus.profile.navigation.navigateProfileMain
import com.wepli.mypage.menus.profile.navigation.profileMainGraph
import com.wepli.playlist.navigation.navigateToPlaylistDetail
import com.wepli.playlist.navigation.playlistDetailGraph
import com.wepli.relaylist.navigation.navigateToRelaylistDetail
import com.wepli.relaylist.navigation.relaylistDetailGraph
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
    ScreenNameTracker(navController) {
        NavHost(
            navController = navController,
            startDestination = startDestination,
            enterTransition = {
                ScreenTransitions.defaultEnterTransition(500)
            },
            exitTransition = {
                ScreenTransitions.defaultExitTransition(500)
            }
        ) {
            // 홈 Graph
            homeGraph(
                navOnPlaylistDetail = { playlistId ->
                    navController.navigateToPlaylistDetail(
                        playlistId
                    )
                },
                navOnRelaylistDetail = { relaylistId ->
                    navController.navigateToRelaylistDetail(
                        relaylistId
                    )
                }
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

            // 릴레이리스트 Graph
            relaylistGraph(navController)

            // 마이페이지 Graph
            mypageGraph(navController, goToLoginActivity)

            // 개발자 모드 Graph
            // TODO: 중첩 그래프로 관리 필요성 검토
            devModeGraph(navController)

            devModeFcmGraph(navController)

            networkLogGraph(navController)

            // 프로필 Graph
            profileGraph(navController)

            // 포토카드 Graph
            photoCardGraph(navController)

            // 노래 Graph
            songInfoGraph { navController.navigateUp() }
        }
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
        navOnBack = { navController.navigateUp() },
        navigateBackWithSelectedSongs = { selectedSongs ->
            navController.navigateBackWithSelectedSongs(selectedSongs)
        },
        navigateSongInfo = { song ->
            navController.navigateToSongInfo(song)
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
        navOnBack = { navController.navigateUp() }
    )
    communityWriteGraph(
        navOnBack = { navController.navigateUp() },
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
        navOnBack = { navController.navigateUp() }
    )
}

// 릴레이리스트 Graph
fun NavGraphBuilder.relaylistGraph(navController: NavController) {
    relaylistDetailGraph { navController.popBackStack() }
}

// 마이페이지 Graph
fun NavGraphBuilder.mypageGraph(
    navController: NavHostController,
    goToLoginActivity: () -> Unit,
) {
    mypageMainGraph(
        navOnAppInfo = { navController.navigateToAppInfo() },
        navOnPhotoCard = { navController.navigateToPhotoCardMain() },
        navOnProfile = { navController.navigateProfileMain() },
        navOnDevMode = { navController.navigateToDevModeMain() },
        goToLoginActivity = { goToLoginActivity() }
    )
    mypageAppInfoGraph(
        navOnBack = { navController.navigateUp() }
    )
}

// 프로필 Graph
fun NavGraphBuilder.profileGraph(navController: NavController) {
    profileMainGraph(
        navOnBack = { navController.navigateUp() }
    )
}

// 개발자 모드 Graph

fun NavGraphBuilder.devModeGraph(navController: NavController) {
    devModeMainGraph(
        navOnBack = { navController.navigateUp() },
        navOnNetworkLog = { navController.navigateToNetworkLogMain() },
        navOnSendFcmPush = { navController.navigateToDevModeFcmMain(BuildConfig.FIREBASE_PROJECT_ID) },
    )
}

fun NavGraphBuilder.devModeFcmGraph(navController: NavController) {
    devModeFcmGraph(
        navOnBack = { navController.navigateUp() }
    )
}

fun NavGraphBuilder.networkLogGraph(navController: NavController) {
    networkLogMainGraph(
        navOnNetworkLogDetail = { apiLogId ->
            navController.navigateToNetworkLogDetail(apiLogId)
        },
        navOnBack = { navController.navigateUp() }
    )

    networkLogDetailGraph(
        navOnBack = { navController.navigateUp() }
    )
}

// 포토카드 Graph
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
        navOnPhotoCardResultScreen = {
            navController.navigateToPhotoCardResult(it)
        }
    )
    photoCardResultGraph(
        navOnBack = { navController.popBackStack() }
    )
}