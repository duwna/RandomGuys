package com.example.randomguys.presentation.screens.main.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CircleTextList(modifier: Modifier, items: List<String>) {

    // used to calculate text offset because we can't get size of parent immediately
    var parentSizePx by remember { mutableStateOf(IntSize.Zero) }
    val density = LocalDensity.current.density

    Box(
        modifier = modifier.onSizeChanged { parentSizePx = it / density },
        contentAlignment = Alignment.Center
    ) {
        if (parentSizePx != IntSize.Zero) {
            items.forEachIndexed { index, item ->
                Text(
                    text = item,
                    modifier = Modifier.offset(
                        x = calculateOffset(items.size, index, parentSizePx.width, ::cos),
                        y = calculateOffset(items.size, index, parentSizePx.height, ::sin)
                    )
                )
            }
        }
    }
}

private inline fun calculateOffset(
    listSize: Int,
    index: Int,
    parentSize: Int,
    angleTransform: (Double) -> Double
): Dp {
    val sweepAngle = 360.0 / listSize
    val textPositionRadian = Math.toRadians(sweepAngle * index + sweepAngle / 2)

    return (angleTransform(textPositionRadian) * parentSize / Math.PI).dp
}

private operator fun IntSize.div(other: Float) = IntSize(
    width = (width.toFloat() / other).toInt(),
    height = (height.toFloat() / other).toInt()
)
