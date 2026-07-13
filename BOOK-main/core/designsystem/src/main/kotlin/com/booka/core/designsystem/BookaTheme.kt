package com.booka.core.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = BookaColor.Primary,
    onPrimary = BookaColor.OnPrimary,
    background = BookaColor.Background,
    onBackground = BookaColor.OnBackground,
    surface = BookaColor.Surface,
    surfaceVariant = BookaColor.SurfaceVariant,
    error = BookaColor.Error,
)

private val DarkColors = darkColorScheme(
    primary = BookaColor.PrimaryDark,
    onPrimary = BookaColor.OnBackgroundDark,
    background = BookaColor.BackgroundDark,
    onBackground = BookaColor.OnBackgroundDark,
    surface = BookaColor.SurfaceDark,
    surfaceVariant = BookaColor.SurfaceVariantDark,
    error = BookaColor.ErrorDark,
)

@Composable
fun BookaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colorScheme,
        typography = BookaTypography,
        shapes = BookaShapes,
        content = content,
    )
}
