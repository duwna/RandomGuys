package com.example.randomguys.ui.main

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.randomguys.R
import com.example.randomguys.models.RouletteItem

@Composable
fun AnimatedRoulette(
    textPositionFraction: Int,
    items: List<RouletteItem>,
    modifier: Modifier = Modifier
) {
    var isAnimating by remember { mutableStateOf(false) }

    val rotation by animateFloatAsState(
        targetValue = 100f,
        animationSpec = tween(durationMillis = 300)
    )

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        CircleRoulette(
            textPositionFraction = textPositionFraction,
            items = items,
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    isAnimating = true
                }
        )

        Image(
            painter = painterResource(id = R.drawable.icon_default_roulette_indicator),
            contentDescription = "Roulette indicator",
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)
                .rotate(rotation)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AnimatedRoulettePreview() {
    AnimatedRoulette(
        modifier = Modifier.size(300.dp),
        textPositionFraction = 300,
        items = listOf(
            RouletteItem("AAAA", Color.Blue),
            RouletteItem("BBBBBBB", Color.Red),
            RouletteItem("CCC", Color.Yellow)
        )
    )
}
