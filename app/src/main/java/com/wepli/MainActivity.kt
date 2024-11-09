package com.wepli

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.wepli.navigation.BottomNavRoute
import com.wepli.navigation.SetUpNavGraph
import dagger.hilt.android.AndroidEntryPoint
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

    val isBottomTabVisible by remember {
        derivedStateOf { currentRoute in bottomNavItems.map { it.route } }
    }

    Scaffold(
        containerColor = WePLiTheme.color.black,
        bottomBar = {
            if (isBottomTabVisible) {
                BottomNavigationBar(
                    navItems = bottomNavItems,
                    navController = navController,
                    currentRoute = currentRoute
                )
            }
        }
    ) {
        SetUpNavGraph(navController = navController, startDestination = BottomNavRoute.Home.route)
    }
}

@Composable
fun BottomNavigationBar(
    navItems: List<BottomNavRoute>,
    navController: NavHostController,
    currentRoute: String?,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .background(WePLiTheme.color.black)
    ) {
        NavigationBar(
            modifier = Modifier.height(56.dp),
            containerColor = Color.Transparent,
        ) {
            navItems.forEach { item ->
                val isSelected = currentRoute == item.route
                val iconRes = if (isSelected) item.bottomTabSelectedIcon else item.bottomTabIcon
                val textTypo = WePLiTheme.typo.caption2.copy(
                    fontWeight = FontWeight.Medium,
                )
                val textStyle = if (isSelected) {
                    textTypo.copy(brush = WePLiTheme.color.linear3,)
                } else {
                    textTypo.copy(color = WePLiTheme.color.gray500,)
                }

                NavigationBarItem(
                    icon = {
                        Icon(
                            modifier = Modifier
                                .size(24.dp)
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

@Composable
fun MyPageScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "MyPage")
    }
}