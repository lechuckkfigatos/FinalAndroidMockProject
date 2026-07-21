package com.example.pftandroidmockproject.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.pftandroidmockproject.domain.model.setting.AppFontSize

private val AppFontFamily = FontFamily.Default

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 42.sp,
        lineHeight = 50.sp,
        letterSpacing = 0.sp
    ),
    displayMedium = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp
    ),
    displaySmall = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        lineHeight = 38.sp,
        letterSpacing = 0.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 34.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 30.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 21.sp,
        lineHeight = 27.sp,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.sp
    ),
    titleSmall = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp
    ),
    bodySmall = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 17.sp,
        letterSpacing = 0.sp
    ),
    labelLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp
    ),
    labelMedium = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.sp
    )
)

fun appTypography(fontSize: AppFontSize): Typography {
    val scale = when (fontSize) {
        AppFontSize.SMALL -> 0.92f
        AppFontSize.MEDIUM -> 1f
        AppFontSize.LARGE -> 1.12f
    }

    if (scale == 1f) {
        return Typography
    }

    return Typography.copy(
        displayLarge = Typography.displayLarge.scaledBy(scale),
        displayMedium = Typography.displayMedium.scaledBy(scale),
        displaySmall = Typography.displaySmall.scaledBy(scale),
        headlineLarge = Typography.headlineLarge.scaledBy(scale),
        headlineMedium = Typography.headlineMedium.scaledBy(scale),
        headlineSmall = Typography.headlineSmall.scaledBy(scale),
        titleLarge = Typography.titleLarge.scaledBy(scale),
        titleMedium = Typography.titleMedium.scaledBy(scale),
        titleSmall = Typography.titleSmall.scaledBy(scale),
        bodyLarge = Typography.bodyLarge.scaledBy(scale),
        bodyMedium = Typography.bodyMedium.scaledBy(scale),
        bodySmall = Typography.bodySmall.scaledBy(scale),
        labelLarge = Typography.labelLarge.scaledBy(scale),
        labelMedium = Typography.labelMedium.scaledBy(scale),
        labelSmall = Typography.labelSmall.scaledBy(scale)
    )
}

private fun TextStyle.scaledBy(scale: Float): TextStyle {
    return copy(
        fontSize = (fontSize.value * scale).sp,
        lineHeight = (lineHeight.value * scale).sp
    )
}
