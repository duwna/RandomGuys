package com.example.randomguys.ui.main.composable_items

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.randomguys.R
import com.example.randomguys.models.RouletteItem
import kotlinx.coroutines.launch

@Composable
fun AnimatedRoulette(
    items: List<RouletteItem>,
    modifier: Modifier = Modifier,
    rotationDuration: Int = 5000,
    rotationsCount: Int = 10,
    onItemSelected: (RouletteItem?) -> Unit = {}
) {
    val animationScope = rememberCoroutineScope()
    val animatedRotation = remember { Animatable(0f) }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {

        CircleRoulette(
            items = items,
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .clickable(enabled = !animatedRotation.isRunning) {
                    onItemSelected.invoke(null)

                    animationScope.launch {
                        val targetAngle = (0 until 360).random()

                        animatedRotation.animateTo(
                            targetValue = rotationsCount * 360f + targetAngle,
                            animationSpec = tween(
                                durationMillis = rotationDuration,
                                easing = LinearOutSlowInEasing
                            ),
                        )

                        animatedRotation.snapTo(targetAngle.toFloat())
                        onItemSelected.invoke(getSelectedItem(items, targetAngle))
                    }
                }
        )

        Image(
            painter = painterResource(id = R.drawable.icon_default_roulette_indicator),
            contentDescription = "Roulette indicator",
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)
                .rotate(animatedRotation.value)
        )
    }
}

private fun getSelectedItem(items: List<RouletteItem>, targetAngle: Int): RouletteItem {
    val step = 360 / items.size
    val selectedIndex = targetAngle / step

    return items[selectedIndex]
}

@Preview(showBackground = true)
@Composable
private fun AnimatedRoulettePreview() {
    AnimatedRoulette(
        items = listOf(
            RouletteItem("AAAA", Color.Blue),
            RouletteItem("BBBBBBB", Color.Red),
            RouletteItem("CCC", Color.Yellow)
        ),
        modifier = Modifier.size(300.dp)
    )
}
