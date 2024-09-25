package theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.wepli.wepli.designsystem.R
import org.w3c.dom.Text

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

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

    val caption: TextStyle,
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
        subTItle7: TextStyle = TextStyle(
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
        caption: TextStyle = TextStyle(
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
        subTitle7 = subTItle7,
        body1 = body1,
        body2 = body2,
        body3 = body3,
        body4 = body4,
        body5 = body5,
        caption = caption,
        overline = overline,
    )
}