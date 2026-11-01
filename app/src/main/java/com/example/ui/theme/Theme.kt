package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme =
  darkColorScheme(
    primary = SaffronLight,
    onPrimary = DarkBackground,
    primaryContainer = SaffronDark,
    onPrimaryContainer = SaffronLight,
    secondary = AmberLight,
    onSecondary = DarkBackground,
    background = DarkBackground,
    onBackground = DarkTextPrimary,
    surface = DarkSurface,
    onSurface = DarkTextPrimary,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkTextSecondary,
    outline = DarkSurfaceVariant,
    tertiary = VegGreenLight
  )

private val LightColorScheme =
  lightColorScheme(
    primary = SaffronPrimary,
    onPrimary = CreamSurface,
    primaryContainer = AmberLight,
    onPrimaryContainer = AmberRoasted,
    secondary = AmberRoasted,
    onSecondary = CreamSurface,
    background = CreamBackground,
    onBackground = TextPrimary,
    surface = CreamSurface,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceMuted,
    onSurfaceVariant = TextSecondary,
    outline = CreamCardBorder,
    tertiary = VegGreen
  )

@Composable
fun JabsonsBhiwaniTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}
