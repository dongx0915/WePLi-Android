package theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wepli.wepli.designsystem.R
import extensions.toSp

internal val LocalWePLiTypography = staticCompositionLocalOf { WePLiTypography() }

val pretendard = FontFamily(
    Font(R.font.pretendard_thin, FontWeight.Thin, FontStyle.Normal),
    Font(R.font.pretendard_extralight, FontWeight.ExtraLight, FontStyle.Normal),
    Font(R.font.pretendard_light, FontWeight.Light, FontStyle.Normal),
    Font(R.font.pretendard_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.pretendard_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.pretendard_semibold, FontWeight.SemiBold, FontStyle.Normal),
    Font(R.font.pretendard_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.pretendard_extrabold, FontWeight.ExtraBold, FontStyle.Normal),
    Font(R.font.pretendard_black, FontWeight.Black, FontStyle.Normal),
)

/**
 * 폰트 Scale은 고정으로 사용 (dp와 동일)
 * - 참고) Theme > fontScale = 1f
 */
class WePLiTypography internal constructor(
    val title1: TextStyle,
    val title2: TextStyle,
    val title3: TextStyle,

    val subTitle1: TextStyle,
    val subTitle2: TextStyle,
    val subTitle3: TextStyle,
    val subTitle4: TextStyle,
    val subTitle5: TextStyle,
    val subTitle6: TextStyle,
    val subTitle7: TextStyle,

    val body1: TextStyle,
    val body2: TextStyle,
    val body3: TextStyle,
    val body4: TextStyle,
    val body5: TextStyle,
    val body6: TextStyle,

    val caption1: TextStyle,
    val caption2: TextStyle,
    val overline: TextStyle,
) {
    constructor(
        defaultFontFamily: FontFamily = pretendard,
        title1: TextStyle = TextStyle(
            fontFamily = defaultFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 28.sp
        ),
        title2: TextStyle = TextStyle(
            fontFamily = defaultFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp
        ),
        title3: TextStyle = TextStyle(
            fontFamily = defaultFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp
        ),
        subTitle1: TextStyle = TextStyle(
            fontFamily = defaultFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp
        ),
        subTitle2: TextStyle = TextStyle(
            fontFamily = defaultFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        ),
        subTitle3: TextStyle = TextStyle(
            fontFamily = defaultFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),
        subTitle4: TextStyle = TextStyle(
            fontFamily = defaultFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp
        ),
        subTitle5: TextStyle = TextStyle(
            fontFamily = defaultFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp
        ),
        subTitle6: TextStyle = TextStyle(
            fontFamily = defaultFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp
        ),
        subTitle7: TextStyle = TextStyle(
            fontFamily = defaultFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 10.sp
        ),
        body1: TextStyle = TextStyle(
            fontFamily = defaultFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp
        ),
        body2: TextStyle = TextStyle(
            fontFamily = defaultFontFamily,
            fontWeight = FontWeight.Light,
            fontSize = 15.sp
        ),
        body3: TextStyle = TextStyle(
            fontFamily = defaultFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        ),
        body4: TextStyle = TextStyle(
            fontFamily = defaultFontFamily,
            fontWeight = FontWeight.Light,
            fontSize = 14.sp
        ),
        body5: TextStyle = TextStyle(
            fontFamily = defaultFontFamily,
            fontWeight = FontWeight.Light,
            fontSize = 13.sp
        ),
        body6: TextStyle = TextStyle(
            fontFamily = defaultFontFamily,
            fontWeight = FontWeight.Light,
            fontSize = 12.sp
        ),
        caption1: TextStyle = TextStyle(
            fontFamily = defaultFontFamily,
            fontWeight = FontWeight.Light,
            fontSize = 11.sp
        ),
        caption2: TextStyle = TextStyle(
            fontFamily = defaultFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 10.sp
        ),
        overline: TextStyle = TextStyle(
            fontFamily = defaultFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 10.sp
        ),
    ) : this(
        title1 = title1,
        title2 = title2,
        title3 = title3,
        subTitle1 = subTitle1,
        subTitle2 = subTitle2,
        subTitle3 = subTitle3,
        subTitle4 = subTitle4,
        subTitle5 = subTitle5,
        subTitle6 = subTitle6,
        subTitle7 = subTitle7,
        body1 = body1,
        body2 = body2,
        body3 = body3,
        body4 = body4,
        body5 = body5,
        body6 = body6,
        caption1 = caption1,
        caption2 = caption2,
        overline = overline,
    )
}