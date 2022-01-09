package com.neocaptainnemo.cv.ui.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

val Color.Companion.DefaultText
    get() = Color(0xFF212121)

val Color.Companion.SecondaryText
    get() = Color(0xFF757575)

val Color.Companion.SecondaryTextDark
    get() = Color(0xFFB5B5B5)

val Color.Companion.Primary
    get() = Color(0xFF607D8B)

@Composable
fun cvColors(isDark: Boolean = isSystemInDarkTheme()): Colors = Colors(
    primary = Color.Primary,
    primaryVariant = Color(0xFF607D8B),
    secondary = Color(0xFF448AFF),
    secondaryVariant = Color(0xFF448AFF),
    background = if (isDark) Color.Black else Color.White,
    surface = if (isDark) Color(0xFF333333) else Color.White,
    error = Color.White,
    onPrimary = Color.White,
    onSecondary = Color(0xFF757575),
    onBackground = if (isDark) Color.White else Color.DefaultText,
    onSurface = if (isDark) Color.White else Color.DefaultText,
    onError = Color.White,
    isLight = isDark.not()
)

@Composable
fun TextStyle.Companion.primary16(isDarkTheme: Boolean = isSystemInDarkTheme()) =
    TextStyle(if (isDarkTheme) Color.White else Color.DefaultText, 16.sp)

@Composable
fun TextStyle.Companion.primary20(isDarkTheme: Boolean = isSystemInDarkTheme()) =
    TextStyle(if (isDarkTheme) Color.White else Color.DefaultText, 20.sp)

@Composable
fun TextStyle.Companion.secondary14(isDarkTheme: Boolean = isSystemInDarkTheme()) =
    TextStyle(if (isDarkTheme) Color.SecondaryTextDark else Color.SecondaryText, 14.sp)

val TextStyle.Companion.White16
    get() = TextStyle(Color.White, 16.sp)

val TextStyle.Companion.White14
    get() = TextStyle(Color.White, 14.sp)

val TextStyle.Companion.Link14
    get() = TextStyle(Color.Blue, 14.sp)
