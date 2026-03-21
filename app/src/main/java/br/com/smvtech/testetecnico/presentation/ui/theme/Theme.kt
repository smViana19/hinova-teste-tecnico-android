package br.com.smvtech.testetecnico.presentation.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Design system only specifies a light-themed "Architectural Ledger" style.
// We map the specified colors to the Material 3 slots.
private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = Color.White, // Dark container needs light text

    // Secondary is not explicitly defined as a color, but used in components.
    // We'll map it to Primary to maintain brand consistency or use a default.
    secondary = Primary,
    onSecondary = OnPrimary,

    tertiary = Primary,
    onTertiary = OnPrimary,

    background = Surface,
    onBackground = OnSurface,

    surface = Surface,
    onSurface = OnSurface,

    surfaceVariant = SurfaceContainerHighest,
    onSurfaceVariant = OnSurface,

    outlineVariant = OutlineVariant,
    surfaceTint = SurfaceTint,

    // Extended surface roles (Available in newer Material3 versions)
    surfaceContainerLowest = SurfaceContainerLowest,
    surfaceContainerLow = SurfaceContainerLow,
    // surfaceContainer = ... (Default)
    // surfaceContainerHigh = ... (Default)
    surfaceContainerHighest = SurfaceContainerHighest,
    surfaceDim = SurfaceDim,
)

@Composable
fun AppTheme(
    // We remove the unused parameters to clean up the warnings. 
    // The design system ignores system dark mode and dynamic colors.
    content: @Composable () -> Unit
) {
    // The design system is strictly defined with specific colors. 
    // We use the LightColorScheme regardless of system setting to preserve the "Architectural Ledger" look.
    val colorScheme = LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // We use the primary color for status bar to maintain brand authority,
            // or we could use Color.Transparent for a full edge-to-edge look if the layout supports it.
            // For now, we sync with the primary brand color.
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false // Dark status bar for dark primary
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}