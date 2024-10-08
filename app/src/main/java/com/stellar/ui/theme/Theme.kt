package com.stellar.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)


private val MainColorScheme = lightColorScheme(
    primary = PurpleFont,
    primaryContainer = Color.White,
    secondaryContainer = Color.White,
    secondary = PurpleFont,
    tertiary = PurpleFont,
    background = Color.White
)

@Composable
fun StellarTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {

    val colorScheme = MainColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,


    )
}

/*when {
dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
    val context = LocalContext.current
    if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
}

darkTheme -> DarkColorScheme
else -> LightColorScheme*/

