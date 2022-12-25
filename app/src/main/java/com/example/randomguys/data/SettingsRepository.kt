package com.example.randomguys.data

import androidx.compose.ui.graphics.Color
import com.example.randomguys.models.RouletteItem
import javax.inject.Inject

class SettingsRepository @Inject constructor() {

    val items = listOf(
        RouletteItem("AAAA", Color.Blue),
        RouletteItem("ddd", Color.Red),
        RouletteItem("C", Color.Yellow),
        RouletteItem("DD", Color.Green),
        RouletteItem("EEEEE", Color.Blue),
        RouletteItem("DD", Color.Red)
    )
}
