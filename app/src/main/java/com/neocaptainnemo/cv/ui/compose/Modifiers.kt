package com.neocaptainnemo.cv.ui.compose

import androidx.compose.foundation.ScrollState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import kotlin.math.max
import kotlin.math.min

class ScrollsHolder(var previousScroll: Int = 0, var res: Int = 0)

fun Modifier.translateOnScroll(
    scrollState: ScrollState,
    holder: ScrollsHolder,
) = Modifier.layout { measurable, constraints ->

    val placeable = measurable.measure(constraints)

    val dy = scrollState.value.toInt() - holder.previousScroll

    holder.previousScroll = scrollState.value.toInt()

    holder.res = max(min(holder.res + dy, placeable.height), 0)

    layout(placeable.width, placeable.height) {
        // Where the composable gets placed

        placeable.placeRelative(0, holder.res)
    }
}
