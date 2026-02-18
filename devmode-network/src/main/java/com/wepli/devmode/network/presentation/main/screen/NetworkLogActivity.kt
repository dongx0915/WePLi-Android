package com.wepli.devmode.network.presentation.main.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.wepli.devmode.network.navigation.NetworkLogNavGraph
import dagger.hilt.android.AndroidEntryPoint
import theme.WePLiTheme

@AndroidEntryPoint
class NetworkLogActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val navController: NavHostController = rememberNavController()

            WePLiTheme {
                NetworkLogNavGraph(
                    navController = navController,
                    onFinishActivity = { finish() }
                )
            }
        }
    }
}