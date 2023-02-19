package com.example.randomguys.presentation.screens.main

import com.example.randomguys.domain.models.RouletteItem
import com.example.randomguys.domain.models.Settings

data class MainViewState(
    val rouletteItems: List<RouletteItem> = emptyList(),
    val autoRouletteState: AutoRouletteState = AutoRouletteState.Off,
    val indicatorState: IndicatorState = IndicatorState()
) {
    data class IndicatorState(
        val isAnimating: Boolean = false,
        val rotationDuration: Int = Settings.DEFAULT_ROTATION_DURATION_SECONDS,
        val rotationsCount: Int = Settings.DEFAULT_ROTATIONS_COUNT,
        val currentRotationAngle: Int? = null
    )

    sealed class AutoRouletteState {

        object Off : AutoRouletteState()

        object Playing : AutoRouletteState()

        data class Success(val rouletteItems: List<RouletteItem>) : AutoRouletteState()
    }

    fun getSelectedRouletteItem(): RouletteItem? {
        val angle = indicatorState.currentRotationAngle ?: return null

        val step = 360 / rouletteItems.size
        val selectedIndex = angle / step

        return rouletteItems[selectedIndex]
    }

    val isAutoRouletteFinished
        get() = autoRouletteState == AutoRouletteState.Playing && rouletteItems.size == 2

    val needShowStopIcon
        get() = autoRouletteState == AutoRouletteState.Playing || indicatorState.isAnimating
}
