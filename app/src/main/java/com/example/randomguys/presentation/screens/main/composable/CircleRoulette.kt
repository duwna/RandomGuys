package com.example.randomguys.presentation.screens.main.composable

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.with
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.randomguys.domain.models.RouletteItem

@Composable
fun CircleRoulette(
    items: List<RouletteItem>,
    modifier: Modifier = Modifier
) {
    val sweepAngle = 360f / items.size

    @OptIn(ExperimentalAnimationApi::class)
    AnimatedContent(
        targetState = items,
        transitionSpec = {
            // TODO
            fadeIn(animationSpec = tween(220, delayMillis = 90)) +
                    scaleIn(initialScale = 0.92f, animationSpec = tween(220, delayMillis = 90)) with
                    fadeOut(animationSpec = tween(220))
        }
    ) {
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
    }

    CircleTextList(
        modifier = modifier,
        items = items
    )
}

@Preview(showBackground = true)
@Composable
private fun CircleRoulettePreview() {
    CircleRoulette(
        items = listOf(
            RouletteItem("AAAA", Color.Blue),
            RouletteItem("ddd", Color.Red),
            RouletteItem("C", Color.Yellow),
            RouletteItem("DD", Color.Green),
            RouletteItem("EEEEE", Color.Blue),
            RouletteItem("DD", Color.Red)
        ),
        modifier = Modifier.size(300.dp)
    )
}
