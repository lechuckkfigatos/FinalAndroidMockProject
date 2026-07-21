package com.example.pftandroidmockproject.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.example.pftandroidmockproject.domain.model.setting.AppAccentColor

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)


val HealthGreen = Color(0xFF007D3E)
val HealthGreenSoft = Color(0xFFDDF5E9)

@Immutable
data class AccentPalette(
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val soft: Color,
    val lightBackgroundTop: Color,
    val lightDivider: Color,
    val darkBackgroundTop: Color,
    val darkHeaderBackground: Color,
    val darkDivider: Color
)

@Immutable
data class HealthColorScheme(
    val accent: Color,
    val accentSoft: Color,
    val backgroundTop: Color,
    val backgroundBottom: Color,
    val headerBackground: Color,
    val card: Color,
    val fieldBackground: Color,
    val secondaryText: Color,
    val divider: Color
)

fun accentPalette(accentColor: AppAccentColor): AccentPalette {
    return when (accentColor) {
        AppAccentColor.GREEN -> AccentPalette(
            primary = Color(0xFF007D3E),
            onPrimary = Color.White,
            secondary = Color(0xFF2D7F58),
            soft = Color(0xFFDDF5E9),
            lightBackgroundTop = Color(0xFFE9F8F1),
            lightDivider = Color(0xFFB8DEC9),
            darkBackgroundTop = Color(0xFF07130F),
            darkHeaderBackground = Color(0xFF0B1B15),
            darkDivider = Color(0xFF355346)
        )

        AppAccentColor.RED -> AccentPalette(
            primary = Color(0xFFC23A3A),
            onPrimary = Color.White,
            secondary = Color(0xFFE25C5C),
            soft = Color(0xFFFFE2E2),
            lightBackgroundTop = Color(0xFFFFF0F0),
            lightDivider = Color(0xFFF1B8B8),
            darkBackgroundTop = Color(0xFF180B0B),
            darkHeaderBackground = Color(0xFF241010),
            darkDivider = Color(0xFF5A3030)
        )

        AppAccentColor.YELLOW -> AccentPalette(
            primary = Color(0xFFC47A00),
            onPrimary = Color.White,
            secondary = Color(0xFFE0A01C),
            soft = Color(0xFFFFF1C7),
            lightBackgroundTop = Color(0xFFFFF7DE),
            lightDivider = Color(0xFFEBD48D),
            darkBackgroundTop = Color(0xFF171207),
            darkHeaderBackground = Color(0xFF221A09),
            darkDivider = Color(0xFF594515)
        )

        AppAccentColor.BLUE -> AccentPalette(
            primary = Color(0xFF2367C9),
            onPrimary = Color.White,
            secondary = Color(0xFF4F8BE8),
            soft = Color(0xFFDDEBFF),
            lightBackgroundTop = Color(0xFFEAF3FF),
            lightDivider = Color(0xFFB6CFF1),
            darkBackgroundTop = Color(0xFF08111F),
            darkHeaderBackground = Color(0xFF0D1A2D),
            darkDivider = Color(0xFF2A4770)
        )

        AppAccentColor.PURPLE -> AccentPalette(
            primary = Color(0xFF7A4ACB),
            onPrimary = Color.White,
            secondary = Color(0xFF9A74E3),
            soft = Color(0xFFEDE4FF),
            lightBackgroundTop = Color(0xFFF4EDFF),
            lightDivider = Color(0xFFD1BEF0),
            darkBackgroundTop = Color(0xFF130D1F),
            darkHeaderBackground = Color(0xFF1C132D),
            darkDivider = Color(0xFF49376E)
        )
    }
}

fun lightHealthColors(palette: AccentPalette) = HealthColorScheme(
    accent = palette.primary,
    accentSoft = palette.soft,
    backgroundTop = palette.lightBackgroundTop,
    backgroundBottom = Color(0xFFF8FAFB),
    headerBackground = palette.lightBackgroundTop.copy(alpha = 0.96f),
    card = Color.White,
    fieldBackground = Color(0xFFF0F2F4),
    secondaryText = Color(0xFF748196),
    divider = palette.lightDivider
)

fun darkHealthColors(palette: AccentPalette) = HealthColorScheme(
    accent = palette.primary,
    accentSoft = palette.primary.copy(alpha = 0.18f),
    backgroundTop = palette.darkBackgroundTop,
    backgroundBottom = Color(0xFF101815),
    headerBackground = palette.darkHeaderBackground,
    card = Color(0xFF17211D),
    fieldBackground = Color(0xFF22302B),
    secondaryText = Color(0xFFAAB7B1),
    divider = palette.darkDivider
)

val LightHealthColors = lightHealthColors(accentPalette(AppAccentColor.GREEN))
val DarkHealthColors = darkHealthColors(accentPalette(AppAccentColor.GREEN))

val LocalHealthColors = staticCompositionLocalOf {
    LightHealthColors
}

val HealthAccent: Color
    @Composable
    @ReadOnlyComposable
    get() = LocalHealthColors.current.accent

val HealthAccentSoft: Color
    @Composable
    @ReadOnlyComposable
    get() = LocalHealthColors.current.accentSoft

val HealthBackgroundTop: Color
    @Composable
    @ReadOnlyComposable
    get() = LocalHealthColors.current.backgroundTop

val HealthBackgroundBottom: Color
    @Composable
    @ReadOnlyComposable
    get() = LocalHealthColors.current.backgroundBottom

val HealthHeaderBackground: Color
    @Composable
    @ReadOnlyComposable
    get() = LocalHealthColors.current.headerBackground

val HealthCardBackground: Color
    @Composable
    @ReadOnlyComposable
    get() = LocalHealthColors.current.card

val HealthFieldBackground: Color
    @Composable
    @ReadOnlyComposable
    get() = LocalHealthColors.current.fieldBackground

val HealthSecondaryText: Color
    @Composable
    @ReadOnlyComposable
    get() = LocalHealthColors.current.secondaryText

val HealthDivider: Color
    @Composable
    @ReadOnlyComposable
    get() = LocalHealthColors.current.divider
