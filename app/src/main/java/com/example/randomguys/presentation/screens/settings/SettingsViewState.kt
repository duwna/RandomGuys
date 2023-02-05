package com.example.randomguys.presentation.screens.settings

import com.example.randomguys.domain.models.RouletteGroup
import com.example.randomguys.domain.models.Settings.Companion.DEFAULT_ROTATIONS_COUNT
import com.example.randomguys.domain.models.Settings.Companion.DEFAULT_ROTATION_DURATION_SECONDS
import com.example.randomguys.domain.models.Settings.Companion.rotationDurationRange
import com.example.randomguys.domain.models.Settings.Companion.rotationsCountRange
import com.example.randomguys.presentation.utils.SliderFractionUtils

data class SettingsViewState(
    val rotationDuration: Int = DEFAULT_ROTATION_DURATION_SECONDS,
    val rotationsCount: Int = DEFAULT_ROTATIONS_COUNT,
    val groups: List<RouletteGroup> = emptyList(),
    val selectedGroupId: String? = groups.firstOrNull()?.id
) {

    val durationSliderValue: Float
        get() = SliderFractionUtils.getSliderFraction(rotationDurationRange, rotationDuration)

    val rotationsCountSliderValue: Float
        get() = SliderFractionUtils.getSliderFraction(rotationsCountRange, rotationsCount)

}
