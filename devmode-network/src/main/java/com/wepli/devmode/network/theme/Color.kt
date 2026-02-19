package com.wepli.devmode.network.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

@Stable val White = Color(0xFFFFFFFF)
@Stable val Gray900 = Color(0xFFF0F0F0)
@Stable val Gray800 = Color(0xFFDCDCDC)
@Stable val Gray700 = Color(0xFFC2C2C2)
@Stable val Gray600 = Color(0xFFA3A3A3)
@Stable val Gray500 = Color(0xFF767676)
@Stable val Gray400 = Color(0xFF535353)
@Stable val Gray300 = Color(0xFF3C3D47)
@Stable val Gray200 = Color(0xFF2F2F38)
@Stable val Gray150 = Color(0xFF2A2A33)
@Stable val Gray100 = Color(0xFF212129)
@Stable val Gray050 = Color(0xFF18181D)
@Stable val Gray000 = Color(0xFF141417)
@Stable val Red500 = Color(0xFFC53C3C)
@Stable val Black = Color(0xFF000000)

@Immutable
data class Colors(
    val white: Color,
    val gray900: Color,
    val gray800: Color,
    val gray700: Color,
    val gray600: Color,
    val gray500: Color,
    val gray400: Color,
    val gray300: Color,
    val gray200: Color,
    val gray150: Color,
    val gray100: Color,
    val gray050: Color,
    val gray000: Color,
    val red500: Color,
    val black: Color,
) {
    companion object {
        fun lightColors(
            white: Color = White,
            gray800: Color = Gray800,
            gray700: Color = Gray700,
            gray600: Color = Gray600,
            gray500: Color = Gray500,
            gray400: Color = Gray400,
            gray300: Color = Gray300,
            gray200: Color = Gray200,
            gray150: Color = Gray150,
            gray100: Color = Gray100,
            gray050: Color = Gray050,
            gray000: Color = Gray000,
            red500: Color = Red500,
            black: Color = Black,
        ): Colors {
            return Colors(
                white = white,
                gray900 = Gray900,
                gray800 = gray800,
                gray700 = gray700,
                gray600 = gray600,
                gray500 = gray500,
                gray400 = gray400,
                gray300 = gray300,
                gray200 = gray200,
                gray150 = gray150,
                gray100 = gray100,
                gray050 = gray050,
                gray000 = gray000,
                red500 = red500,
                black = black,
            )
        }

        fun darkColors(
            white: Color = White,
            gray800: Color = Gray800,
            gray700: Color = Gray700,
            gray600: Color = Gray600,
            gray500: Color = Gray500,
            gray400: Color = Gray400,
            gray300: Color = Gray300,
            gray200: Color = Gray200,
            gray150: Color = Gray150,
            gray100: Color = Gray100,
            gray050: Color = Gray050,
            gray000: Color = Gray000,
            red500: Color = Red500,
            black: Color = Black,
        ): Colors {
            return Colors(
                white = white,
                gray900 = Gray900,
                gray800 = gray800,
                gray700 = gray700,
                gray600 = gray600,
                gray500 = gray500,
                gray400 = gray400,
                gray300 = gray300,
                gray200 = gray200,
                gray100 = gray100,
                gray150 = gray150,
                gray050 = gray050,
                gray000 = gray000,
                red500 = red500,
                black = black,
            )
        }
    }
}