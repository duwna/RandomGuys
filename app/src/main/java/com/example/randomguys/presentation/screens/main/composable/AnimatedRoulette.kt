package com.example.randomguys.presentation.screens.main.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.randomguys.domain.models.RouletteItem
import com.example.randomguys.presentation.screens.main.MainViewState

@Composable
fun AnimatedRoulette(
    items: List<RouletteItem>,
    modifier: Modifier = Modifier,
    indicatorState: MainViewState.IndicatorState = MainViewState.IndicatorState(),
    onAngleChanged: (Int?) -> Unit = {}
) {

    Box(modifier = modifier, contentAlignment = Alignment.Center) {

        CircleRoulette(
            items = items,
            modifier = Modifier.fillMaxSize()
        )

        RotatingIndicator(
            onAngleChanged = onAngleChanged,
            indicatorState = indicatorState,
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)
        )
    }
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
        modifier = Modifier.size(300.dp),
    )
}
