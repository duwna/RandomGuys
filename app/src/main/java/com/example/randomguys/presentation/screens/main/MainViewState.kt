package com.example.randomguys.presentation.screens.main

import com.example.randomguys.domain.models.RouletteItem

data class MainViewState(
    val rouletteItems: List<RouletteItem> = emptyList(),
    val rouletteRotationDuration: Int = 5000,
    val rouletteRotationsCount: Int = 10,
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
