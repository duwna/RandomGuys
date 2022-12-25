package com.example.randomguys.presentation.main

import com.example.randomguys.models.RouletteItem

data class MainViewState(
    val rouletteItems: List<RouletteItem>,
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
