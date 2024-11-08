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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.wepli.navigation.Screen
import theme.WePLiTheme

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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainApp() {
    val navController = rememberNavController()
    val navItems = listOf(
        Screen.Home,
        Screen.Search,
        Screen.Chart,
        Screen.Community,
        Screen.MyPage,
    )

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navItems = navItems, navController = navController)
        }
    ) {
        NavHost(navController = navController, startDestination = Screen.Home.route) {
            composable(Screen.Home.route) {
                HomeScreen()
            }
            composable(Screen.Search.route) {
                SearchScreen()
            }
            composable(Screen.Chart.route) {
                ChartScreen()
            }
            composable(Screen.Community.route) {
                CommunityScreen()
            }
            composable(Screen.MyPage.route) {
                MyPageScreen()
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navItems: List<Screen>, navController: NavHostController) {
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
            val navBackStackEntry = navController.currentBackStackEntryAsState()
            val currentRoute by remember {
                derivedStateOf { navBackStackEntry.value?.destination?.route }
            }

            navItems.forEach { item ->
                val isSelected = currentRoute == item.route
                val textStyle = if (isSelected) {
                    WePLiTheme.typo.caption2.copy(
                        brush = WePLiTheme.color.linear3,
                        fontWeight = FontWeight.Medium
                    )
                } else {
                    WePLiTheme.typo.caption2.copy(
                        color = WePLiTheme.color.gray500,
                        fontWeight = FontWeight.Medium
                    )
                }

                NavigationBarItem(
                    icon = {
                        val icon = if (isSelected) item.selectedIcon else item.icon
                        Icon(
                            modifier = Modifier.size(24.dp).offset(y = 2.dp),
                            imageVector = ImageVector.vectorResource(id = icon),
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
fun HomeScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Home")
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
fun CommunityScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Community")
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