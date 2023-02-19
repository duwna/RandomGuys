package com.example.randomguys.presentation.screens.main.composable

import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.randomguys.R
import com.example.randomguys.presentation.screens.main.MainViewState

@Composable
fun AndroidViewRotatingIndicator(
    modifier: Modifier = Modifier,
    indicatorState: MainViewState.IndicatorState,
    onAngleChanged: (Int?) -> Unit = {},
) {

    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            ImageView(ctx).apply {
                setImageResource(R.drawable.icon_default_roulette_indicator)
            }
        },
        update = { view ->

            view.animate()
                .apply {
                    val targetAngle = (0 until 360).random()

                    rotation(indicatorState.rotationsCount * 360f + targetAngle)

                    withStartAction { onAngleChanged.invoke(null) }
                    withEndAction {
                        onAngleChanged.invoke(targetAngle)

                        view.rotation = targetAngle.toFloat()
                    }

                    duration = indicatorState.rotationDuration.toLong() * 1000
                    interpolator = DecelerateInterpolator()
                }
        })
}
