package com.example.randomguys.presentation.screens.main.composable

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import com.example.randomguys.R
import com.example.randomguys.presentation.screens.main.MainViewState

@Composable
fun RotatingIndicator(
    modifier: Modifier = Modifier,
    indicatorState: MainViewState.IndicatorState,
    onAngleChanged: (Int?) -> Unit = {},
) {
    val animatedRotation = remember { Animatable(indicatorState.currentRotationAngle?.toFloat() ?: 0f) }
    val isAnimating = indicatorState.isAnimating

    LaunchedEffect(isAnimating) {
        if (isAnimating) {
            startAnimating(indicatorState.rotationsCount, indicatorState.rotationDuration, animatedRotation, onAngleChanged)
        } else {
            stopAnimating(animatedRotation)
        }
    }

    Image(
        painter = painterResource(id = R.drawable.icon_default_roulette_indicator),
        contentDescription = "Roulette indicator",
        modifier = modifier.rotate(animatedRotation.value)
    )
}

private suspend fun startAnimating(
    rotationsCount: Int,
    rotationDurationSeconds: Int,
    animatedRotation: Animatable<Float, AnimationVector1D>,
    onAngleChanged: (Int?) -> Unit = {}
) {
    onAngleChanged.invoke(null)

    animatedRotation.stop()

    val targetAngle = (0 until 360).random()

    animatedRotation.animateTo(
        targetValue = rotationsCount * 360f + targetAngle,
        animationSpec = tween(
            durationMillis = rotationDurationSeconds * 1000,
            easing = LinearOutSlowInEasing
        ),
    )

    animatedRotation.snapTo(targetAngle.toFloat())
    onAngleChanged.invoke(targetAngle)
}

private suspend fun stopAnimating(animatedRotation: Animatable<Float, AnimationVector1D>) {
    animatedRotation.stop()
//    animatedRotation.snapTo(0f) TODO
}
