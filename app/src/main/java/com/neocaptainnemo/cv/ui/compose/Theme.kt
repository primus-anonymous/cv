package com.neocaptainnemo.cv.ui.compose

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp


val Color.Companion.DefaultText
    get() = Color(0xFF212121)

val Color.Companion.SecondaryText
    get() = Color(0xFF757575)

val Color.Companion.Primary
    get() = Color(0xFF607D8B)


val CvColors = Colors(
        primary = Color.Primary,
        primaryVariant = Color(0xFF607D8B),
        secondary = Color(0xFF448AFF),
        secondaryVariant = Color(0xFF448AFF),
        background = Color.White,
        surface = Color.White,
        error = Color.White,
        onPrimary = Color.White,
        onSecondary = Color(0xFF757575),
        onBackground = Color.DefaultText,
        onSurface = Color.DefaultText,
        onError = Color.White,
        isLight = true
)

val TextStyle.Companion.Primary16
    get() = TextStyle(Color.DefaultText,
                      16.sp)

val TextStyle.Companion.Primary20
    get() = TextStyle(Color.DefaultText,
                      20.sp)

val TextStyle.Companion.Secondary14
    get() = TextStyle(Color.SecondaryText,
                      14.sp)

val TextStyle.Companion.White16
    get() = TextStyle(Color.White,
                      16.sp)

val TextStyle.Companion.White14
    get() = TextStyle(Color.White,
                      14.sp)

val TextStyle.Companion.Link14
    get() = TextStyle(Color.Blue,
                      14.sp)
