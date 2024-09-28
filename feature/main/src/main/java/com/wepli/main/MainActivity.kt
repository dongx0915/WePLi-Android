package com.wepli.main

import ArtistProfileListItem
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import appbar.WePLiAppBar
import extensions.setStatusBarColor
import model.Artist
import theme.WePLiTheme

class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // enableEdgeToEdge()
        setStatusBarColor(Color.Black, darkIcons = false)
        setContent {
            WePLiTheme {
                MainScreen()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MainScreen() {
    Scaffold(
        containerColor = WePLiTheme.color.black,
        topBar = {
            WePLiAppBar(
                showLogo = true,
                showBackButton = false,
                showSearchButton = true,
                showNotificationButton = true,
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            ArtistLayout()
        }
    }
}

@Preview
@Composable
fun ArtistLayout() {
    val artists = listOf(
        Artist("윤하(Younha/ユンナ)", "https://image.bugsm.co.kr/artist/images/1000/800100/80010025_100.jpg?version=332223&d=20220330143136"),
        Artist("아이유(IU)", "https://image.bugsm.co.kr/artist/images/1000/800491/80049126_068.jpg?version=301040&d=20210325154659"),
        Artist("이창섭", "https://image.bugsm.co.kr/artist/images/1000/801234/80123432.jpg?version=133225&d=20240902175949"),
        Artist("BIGBANG (빅뱅)", "https://image.bugsm.co.kr/artist/images/1000/800200/80020023_078.jpg?version=253655&d=20190704103532"),
        Artist("츄 (CHUU)", "https://image.bugsm.co.kr/artist/images/1000/802980/80298005_010.jpg?version=383827&d=20231018020427"),
        Artist("(여자)아이들", "https://image.bugsm.co.kr/artist/images/1000/200564/20056456.jpg?version=227124&d=20240703113218"),
        Artist("로이킴", "https://image.bugsm.co.kr/artist/images/1000/801377/80137715.jpg?version=112079&d=20240304183844"),
        Artist("이승기", "https://image.bugsm.co.kr/artist/images/1000/721/72184_041.jpg?version=292892&d=20201211184753"),
        Artist("aespa", "https://image.bugsm.co.kr/artist/images/1000/803473/80347326_049.jpg?version=402792&d=20240510104032")
    )

    Column {
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = "위플리 인기 랭킹",
            style = WePLiTheme.typo.subTitle2,
            color = WePLiTheme.color.gray900,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = "위플리 차트에서 인기가 많은 가수들을 모아봤어요",
            style = WePLiTheme.typo.body6,
            color = WePLiTheme.color.gray600,
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 20.dp)
        ) {
            items(artists) { artist ->
                ArtistProfileListItem(artist)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun AppBarPreview() {
    WePLiAppBar(
        title = "타이틀",
    )
}