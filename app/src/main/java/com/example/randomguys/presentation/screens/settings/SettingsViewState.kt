package com.example.randomguys.presentation.screens.settings

import com.example.randomguys.domain.models.RouletteGroup

data class SettingsViewState(
    val selectedDuration: Float = 0f,
    val selectedRotation: Float = 0f,
    val selectedGroupId: Int? = null,
    val groups: List<RouletteGroup> = emptyList()
) {
    val durationText get() = (selectedDuration * 100).toInt().toString()
    val rotationText get() = (selectedRotation * 100).toInt().toString()
}
