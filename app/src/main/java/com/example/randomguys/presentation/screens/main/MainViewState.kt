package com.example.randomguys.presentation.screens.main

import com.example.randomguys.domain.models.RouletteItem
import com.example.randomguys.domain.models.Settings

data class MainViewState(
    val rouletteItems: List<RouletteItem> = emptyList(),
    val rotationDuration: Int = Settings.DEFAULT_ROTATION_DURATION_SECONDS,
    val rotationsCount: Int = Settings.DEFAULT_ROTATIONS_COUNT,
    val rouletteRotationAngle: Int? = null
) {

    val selectedName: String?
        get() {
            rouletteRotationAngle ?: return null

            val step = 360 / rouletteItems.size
            val selectedIndex = rouletteRotationAngle / step

            return rouletteItems[selectedIndex].name
        }

}
