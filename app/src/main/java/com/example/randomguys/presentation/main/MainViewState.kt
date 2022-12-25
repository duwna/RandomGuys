package com.example.randomguys.presentation.main

import com.example.randomguys.models.RouletteItem

data class MainViewState(
    val rouletteItems: List<RouletteItem>,
    val selectedName: String? = null
)
