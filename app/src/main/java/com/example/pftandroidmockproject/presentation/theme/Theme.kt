package com.example.pftandroidmockproject.presentation.theme

import android.content.Context
import android.content.ContextWrapper
import android.content.res.AssetManager
import android.content.res.Configuration
import android.content.res.Resources
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import com.example.pftandroidmockproject.domain.model.setting.AppAccentColor
import com.example.pftandroidmockproject.domain.model.setting.AppFontSize
import com.example.pftandroidmockproject.domain.model.setting.AppLanguage
import com.example.pftandroidmockproject.domain.model.setting.AppThemeMode
import java.util.Locale

private fun darkAppColorScheme(palette: AccentPalette) = darkColorScheme(
    primary = palette.primary,
    onPrimary = palette.onPrimary,
    secondary = palette.secondary,
    tertiary = Color(0xFFE76F51),
    background = Color(0xFF101815),
    onBackground = Color(0xFFE5EEE9),
    surface = Color(0xFF17211D),
    onSurface = Color(0xFFE5EEE9),
    surfaceVariant = Color(0xFF22302B),
    onSurfaceVariant = Color(0xFFAAB7B1),
    outlineVariant = Color(0xFF355346),
    error = Color(0xFFFFB4AB)
)

private fun lightAppColorScheme(palette: AccentPalette) = lightColorScheme(
    primary = palette.primary,
    onPrimary = palette.onPrimary,
    secondary = palette.secondary,
    tertiary = Color(0xFFE76F51),
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    surfaceVariant = Color(0xFFF0F2F4),
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    onSurfaceVariant = Color(0xFF5F6B7A),
    outlineVariant = Color(0xFFD6E5DC)
)

private class LocalizedContextWrapper(
    base: Context,
    private val localizedContext: Context
) : ContextWrapper(base) {
    override fun getAssets(): AssetManager {
        return localizedContext.assets
    }

    override fun getResources(): Resources {
        return localizedContext.resources
    }
}

@Composable
fun PFTAndroidMockProjectTheme(
    language: AppLanguage = AppLanguage.VI,
    themeMode: AppThemeMode = AppThemeMode.SYSTEM,
    fontSize: AppFontSize = AppFontSize.MEDIUM,
    accentColor: AppAccentColor = AppAccentColor.GREEN,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val baseConfiguration = LocalConfiguration.current
    val locale = remember(language) {
        when (language) {
            AppLanguage.EN -> Locale.ENGLISH
            AppLanguage.VI -> Locale.forLanguageTag("vi")
        }
    }

    val localizedConfiguration = remember(
        baseConfiguration,
        locale
    ) {
        Configuration(baseConfiguration).apply {
            setLocale(locale)
        }
    }

    val localizedContext = remember(
        context,
        localizedConfiguration
    ) {
        LocalizedContextWrapper(
            base = context,
            localizedContext = context.createConfigurationContext(localizedConfiguration)
        )
    }

    SideEffect {
        Locale.setDefault(locale)
    }

    val darkTheme = when (themeMode) {
        AppThemeMode.SYSTEM -> isSystemInDarkTheme()
        AppThemeMode.LIGHT -> false
        AppThemeMode.DARK -> true
    }
    val palette = remember(accentColor) {
        accentPalette(accentColor)
    }

    val colorScheme = when {
        darkTheme -> darkAppColorScheme(palette)
        else -> lightAppColorScheme(palette)
    }

    val healthColors = if (darkTheme) {
        darkHealthColors(palette)
    } else {
        lightHealthColors(palette)
    }

    CompositionLocalProvider(
        LocalContext provides localizedContext,
        LocalConfiguration provides localizedConfiguration,
        LocalHealthColors provides healthColors
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = appTypography(fontSize),
            content = content
        )
    }
}
