package com.example.randomguys.presentation.screens.group_edition

import com.example.randomguys.domain.models.RouletteGroup

data class GroupViewState(
    val group: RouletteGroup?
)

sealed class GroupEvent {
    object NavigateUp : GroupEvent()
}
