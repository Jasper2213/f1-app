package be.howest.jasperdesnyder.formulaone.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    //    primary = Purple200,
    //    primaryVariant = Purple700,
    //    secondary = Teal200
    primary = navigationLight,
    onPrimary = onPrimaryDark,
    onSecondary = onSecondaryDark
)

private val LightColorPalette = lightColors(
    primary = navigationLight,
    secondary = navigationLight,
    background = backgroundLight,
    surface = surfaceLight,
//    onBackground = onBackground

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun FormulaOneTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}