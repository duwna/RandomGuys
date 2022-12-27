package com.example.randomguys.presentation.settings.composable_items

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.randomguys.R

@Composable
fun AnimatedIndicator(
    modifier: Modifier = Modifier
) {
    val transition = rememberInfiniteTransition()

    val indicatorRotation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Image(
        painter = painterResource(id = R.drawable.icon_default_roulette_indicator),
        contentDescription = "choose indicator",
        modifier = modifier
            .size(200.dp)
            .background(
                color = MaterialTheme.colorScheme.onBackground,
                shape = CircleShape
            )
            .rotate(indicatorRotation)
    )
}
