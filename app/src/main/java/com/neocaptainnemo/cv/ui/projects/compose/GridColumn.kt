package com.neocaptainnemo.cv.ui.projects.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.FrameManager
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout

@Composable
fun GridColumn(
        modifier: Modifier = Modifier,
        columns: Int,
        children: @Composable() () -> Unit,
) {
    Layout(
            modifier = modifier,
            children = children
    ) { measurables, constraints ->
        // measure and position children given constraints logic here

        val placebales = measurables.mapIndexed { _, measurable ->

            val constraintCopy = constraints.copy(maxWidth = constraints.maxWidth / columns)

            val placeable = measurable.measure(constraintCopy)

            placeable
        }

        val step = constraints.maxWidth / columns
        var xPos = 0

        layout(constraints.maxWidth, placebales.first().height) {
            placebales.forEachIndexed { index, _ ->
                placebales[index].placeRelative(xPos, 0)
                xPos += step
            }
        }
    }
}
