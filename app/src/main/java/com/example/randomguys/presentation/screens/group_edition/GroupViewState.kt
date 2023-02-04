package com.example.randomguys.presentation.screens.group_edition

import com.example.randomguys.domain.models.RouletteGroup

data class GroupViewState(
    val group: RouletteGroup? = null,
    val isColorPickerVisibleForIndex: Int? = null
)

sealed class GroupEvent {
    object NavigateUp : GroupEvent()
}
