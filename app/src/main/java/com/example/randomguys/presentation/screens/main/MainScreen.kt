package com.example.randomguys.presentation.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.randomguys.R
import com.example.randomguys.presentation.screens.main.MainViewState.AutoRouletteState
import com.example.randomguys.presentation.screens.main.composable.AnimatedRoulette
import com.example.randomguys.presentation.screens.main.composable.AutoRouletteSuccessDialog

@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {

        if (state.rouletteItems.isNotEmpty()) {
            AnimatedRoulette(
                items = state.rouletteItems,
                indicatorState = state.indicatorState,
                onAngleChanged = viewModel::onAngleChanged,
                modifier = Modifier
                    .align(Center)
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .padding(30.dp)
                    .shadow(elevation = 10.dp, shape = CircleShape)
                    .clip(CircleShape)
                    .clickable(enabled = !state.indicatorState.isAnimating) { viewModel.onRouletteClicked() }
            )
        }

        FloatingActionButton(
            onClick = viewModel::navigateToSettings,
            containerColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(72.dp)
                .padding(16.dp)
                .align(TopEnd)
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_settings),
                contentDescription = "open settings button",
                modifier = Modifier.size(20.dp)
            )
        }

        FloatingActionButton(
            onClick = viewModel::onBottomButtonPressed,
            containerColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(72.dp)
                .padding(16.dp)
                .align(BottomEnd)
        ) {
            Image(
                painter = painterResource(
                    id = when {
                        state.needShowStopIcon -> R.drawable.icon_stop
                        else -> R.drawable.icon_auto_mode
                    }
                ),
                contentDescription = "auto roulette button",
                modifier = Modifier.size(20.dp)
            )
        }

        val autoRouletteState = state.autoRouletteState

        if (autoRouletteState is AutoRouletteState.Success) {
            AutoRouletteSuccessDialog(
                rouletteItems = autoRouletteState.rouletteItems,
                onDismiss = viewModel::onBottomButtonPressed
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    MainScreen()
}
