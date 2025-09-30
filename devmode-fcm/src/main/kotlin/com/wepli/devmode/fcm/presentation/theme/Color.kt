package com.wepli.devmode.fcm.presentation.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.graphics.Brush
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

@Stable val Linear3 = Brush.linearGradient(
    colorStops = arrayOf(
        Pair(0.0f, Color(0xFF9DABFF)),
        Pair(0.39f, Color(0xFFC8CFFF)),
        Pair(1.0f, Color(0xFFA4FFFF))
    ),
)

class DevModeColors(
    white: Color,
    gray900: Color,
    gray800: Color,
    gray700: Color,
    gray600: Color,
    gray500: Color,
    gray400: Color,
    gray300: Color,
    gray200: Color,
    gray150: Color,
    gray100: Color,
    gray050: Color,
    gray000: Color,
    red500: Color,
    black: Color,
    linear3: Brush,
) {
    var white by mutableStateOf(white, structuralEqualityPolicy())
        internal set
    var gray900 by mutableStateOf(gray900, structuralEqualityPolicy())
        internal set
    var gray800 by mutableStateOf(gray800, structuralEqualityPolicy())
        internal set
    var gray700 by mutableStateOf(gray700, structuralEqualityPolicy())
        internal set
    var gray600 by mutableStateOf(gray600, structuralEqualityPolicy())
        internal set
    var gray500 by mutableStateOf(gray500, structuralEqualityPolicy())
        internal set
    var gray400 by mutableStateOf(gray400, structuralEqualityPolicy())
        internal set
    var gray300 by mutableStateOf(gray300, structuralEqualityPolicy())
        internal set
    var gray200 by mutableStateOf(gray200, structuralEqualityPolicy())
        internal set
    var gray150 by mutableStateOf(gray150, structuralEqualityPolicy())
        internal set
    var gray050 by mutableStateOf(gray050, structuralEqualityPolicy())
        internal set
    var gray100 by mutableStateOf(gray100, structuralEqualityPolicy())
        internal set
    var gray000 by mutableStateOf(gray000, structuralEqualityPolicy())
        internal set
    var red500 by mutableStateOf(red500, structuralEqualityPolicy())
        internal set
    var black by mutableStateOf(black, structuralEqualityPolicy())
        internal set
    var linear3 by mutableStateOf(linear3, structuralEqualityPolicy())
        internal set
}

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
    linear3: Brush = Linear3,
): DevModeColors {
    return DevModeColors(
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
        black = black,
        red500 = red500,
        linear3 = linear3,
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
    linear3: Brush = Linear3,
): DevModeColors {
    return DevModeColors(
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
        linear3 = linear3,
    )
}