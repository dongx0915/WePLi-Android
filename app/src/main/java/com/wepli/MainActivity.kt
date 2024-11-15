package com.wepli

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.wepli.navigation.BottomNavRoute
import com.wepli.navigation.SetUpNavGraph
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import theme.WePLiTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            WePLiTheme {
                MainApp()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "RememberReturnType")
@Composable
fun MainApp() {
    val navController = rememberNavController()
    val bottomNavItems = remember {
        listOf(
            BottomNavRoute.Home,
            BottomNavRoute.Search,
            BottomNavRoute.Chart,
            BottomNavRoute.Community,
            BottomNavRoute.MyPage,
        )
    }
    val currentRoute by navController.currentBackStackEntryAsState().let { state ->
        derivedStateOf { state.value?.destination?.route }
    }

    val isBottomTabVisibleTarget by remember {
        derivedStateOf { currentRoute in bottomNavItems.map { it.route } }
    }

    var isBottomTabVisible by remember { mutableStateOf(true) }
    val visibilityAnimationProgress by animateFloatAsState(
        targetValue = if (isBottomTabVisibleTarget) 1f else 0f,
        label = ""
    )

    // 애니메이션이 끝난 후 isBottomTabVisible 상태 변경
    LaunchedEffect(visibilityAnimationProgress) {
        when(visibilityAnimationProgress) {
            0f -> isBottomTabVisible = false
            1f -> isBottomTabVisible = true
        }
    }

    val hazeState = remember { HazeState() }

    Scaffold(
        containerColor = WePLiTheme.color.black,
        bottomBar = {
            if (isBottomTabVisible) {
                BottomNavigationBar(
                    navItems = bottomNavItems,
                    navController = navController,
                    currentRoute = currentRoute,
                    hazeState = hazeState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .haze(hazeState)
                .fillMaxSize()
        ) {
            SetUpNavGraph(navController = navController, startDestination = BottomNavRoute.Home.route)
        }
    }
}

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    navItems: List<BottomNavRoute>,
    navController: NavHostController,
    currentRoute: String?,
    hazeState: HazeState // hazeState 매개변수 추가
) {
    val bottomNavColor = WePLiTheme.color.black.copy(0.7f)
    Box(
        modifier = modifier
            .hazeChild(
                state = hazeState,
                style = HazeStyle(
                    backgroundColor = bottomNavColor,
                    blurRadius = 24.dp,
                    tint = HazeTint(color = bottomNavColor),
                ),
            )
            .height(56.dp)
    ) {
        NavigationBar(containerColor = Color.Transparent) {
            navItems.forEach { item ->
                val isSelected = currentRoute == item.route
                val iconRes = if (isSelected) item.bottomTabSelectedIcon else item.bottomTabIcon
                val textTypo = WePLiTheme.typo.caption2.copy(
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Medium,
                )
                val textStyle = if (isSelected) {
                    textTypo.copy(brush = WePLiTheme.color.linear3)
                } else {
                    textTypo.copy(color = WePLiTheme.color.gray500)
                }

                NavigationBarItem(
                    icon = {
                        Icon(
                            modifier = Modifier
                                .size(20.dp)
                                .offset(y = 2.dp),
                            imageVector = ImageVector.vectorResource(id = iconRes ?: return@NavigationBarItem),
                            tint = Color.Unspecified,
                            contentDescription = item.title
                        )
                    },
                    label = {
                        Text(modifier = Modifier.offset(y = (-2).dp), text = item.title, style = textStyle)
                    },
                    alwaysShowLabel = true,
                    selected = currentRoute == item.route,
                    onClick = {
                        // 현재 경로와 클릭한 항목의 경로가 다를 때만 네비게이션 수행
                        if (currentRoute == item.route) return@NavigationBarItem

                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Unspecified,
                        unselectedIconColor = Color.Unspecified,
                        selectedTextColor = Color.Unspecified,
                        unselectedTextColor = WePLiTheme.color.gray500,
                        indicatorColor = Color.Transparent,
                    )
                )
            }
        }
    }
}

@Composable
fun SearchScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Search")
    }
}

@Composable
fun ChartScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Chart")
    }
}