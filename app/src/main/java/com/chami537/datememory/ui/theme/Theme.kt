package com.chami537.datememory.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = Black,
    onPrimary = White,
    primaryContainer = Gray100,
    onPrimaryContainer = Black,
    secondary = Gray600,
    onSecondary = White,
    secondaryContainer = Gray50,
    onSecondaryContainer = Gray800,
    tertiary = Gray400,
    onTertiary = White,
    tertiaryContainer = Gray100,
    onTertiaryContainer = Black,
    background = White,
    onBackground = Black,
    surface = White,
    onSurface = Black,
    surfaceVariant = Gray50,
    onSurfaceVariant = Gray600,
    outline = Gray200,
    outlineVariant = Gray100,
    error = Color(0xFFCC0000),
    onError = White,
)

private val DarkColorScheme = darkColorScheme(
    primary = White,
    onPrimary = Black,
    primaryContainer = DarkCard,
    onPrimaryContainer = DarkGray100,
    secondary = DarkGray200,
    onSecondary = Black,
    secondaryContainer = DarkCard,
    onSecondaryContainer = DarkGray100,
    tertiary = DarkGray400,
    onTertiary = Black,
    tertiaryContainer = DarkCard,
    onTertiaryContainer = DarkGray100,
    background = DarkBg,
    onBackground = White,
    surface = DarkSurface,
    onSurface = White,
    surfaceVariant = DarkCard,
    onSurfaceVariant = DarkGray200,
    outline = DarkBorder,
    outlineVariant = DarkCard,
    error = Color(0xFFFF4444),
    onError = Black,
)

@Composable
fun DateMemoryTheme(
    darkTheme: Boolean = androidx.compose.foundation.isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}
