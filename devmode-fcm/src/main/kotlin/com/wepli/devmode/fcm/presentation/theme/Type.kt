package com.wepli.devmode.fcm.presentation.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.wepli.devmode.fcm.R

internal val pretendard = FontFamily(
    Font(R.font.pretendard_thin, FontWeight.Thin, FontStyle.Normal),
    Font(R.font.pretendard_extralight, FontWeight.ExtraLight, FontStyle.Normal),
    Font(R.font.pretendard_light, FontWeight.Light, FontStyle.Normal),
    Font(R.font.pretendard_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.pretendard_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.pretendard_semibold, FontWeight.SemiBold, FontStyle.Normal),
    Font(R.font.pretendard_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.pretendard_extrabold, FontWeight.ExtraBold, FontStyle.Normal),
)

/**
 * WepliTheme의 Typography를 devmode-fcm 모듈에서 사용하기 위한 타입 정의
 * designsystem의 theme/Type.kt에서 복사
 */
data class DevModeTypography(
    val default: TextStyle = TextStyle.Default,
    val title1: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 34.sp
    ),
    val title2: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 30.sp
    ),
    val title3: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 26.sp
    ),
    val subTitle1: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 22.sp
    ),
    val subTitle2: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 20.sp
    ),
    val subTitle3: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 20.sp
    ),
    val subTitle4: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp,
        lineHeight = 19.sp
    ),
    val subTitle5: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 18.sp
    ),
    val subTitle6: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    val subTitle7: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        lineHeight = 16.sp
    ),
    val body1: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 20.sp
    ),
    val body2: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Light,
        fontSize = 15.sp,
        lineHeight = 21.sp
    ),
    val body3: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    val body4: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    val body5: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Light,
        fontSize = 13.sp,
        lineHeight = 17.sp
    ),
    val body6: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp,
        lineHeight = 17.sp
    ),
    val caption1: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Light,
        fontSize = 11.sp,
        lineHeight = 12.sp
    ),
    val caption2: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        lineHeight = 12.sp
    ),
    val overline: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 16.sp
    ),
)

object WepliTheme {
    val typo = DevModeTypography()
}