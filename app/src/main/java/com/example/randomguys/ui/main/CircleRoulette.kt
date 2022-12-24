package com.example.randomguys.ui.main

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.randomguys.models.RouletteItem
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CircleRoulette(
    textPositionFraction: Int,
    items: List<RouletteItem>,
    modifier: Modifier = Modifier
) {
    val sweepAngle = 360f / items.size

    Canvas(modifier = modifier) {
        items.forEachIndexed { index, item ->
            drawArc(
                color = item.color,
                startAngle = sweepAngle * index,
                sweepAngle = sweepAngle,
                useCenter = true
            )
        }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        items.forEachIndexed { index, item ->
            Text(
                text = item.name,
                modifier = Modifier.offset(
                    x = calculateOffset(sweepAngle, index, textPositionFraction, ::cos),
                    y = calculateOffset(sweepAngle, index, textPositionFraction, ::sin)
                )
            )
        }
    }
}

private inline fun calculateOffset(
    sweepAngle: Float,
    index: Int,
    textPositionFraction: Int,
    angleTransform: (Double) -> Double
): Dp {
    val angle = Math.toRadians((sweepAngle.toDouble() * index) + sweepAngle / 2)
    return (angleTransform(angle) * textPositionFraction / Math.PI).dp
}

@Preview(showBackground = true)
@Composable
private fun CircleRoulettePreview() {
    CircleRoulette(
        modifier = Modifier.size(300.dp),
        textPositionFraction = 300,
        items = listOf(
            RouletteItem("AAAA", Color.Blue),
            RouletteItem("ffff", Color.Red),
            RouletteItem("C", Color.Yellow),
            RouletteItem("DD", Color.Green),
            RouletteItem("EEEEE", Color.Blue),
            RouletteItem("DD", Color.Red)
        )
    )
}
