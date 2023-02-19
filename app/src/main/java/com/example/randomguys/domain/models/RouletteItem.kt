package com.example.randomguys.domain.models

import androidx.compose.ui.graphics.Color
import com.example.randomguys.GroupMemberDto
import com.example.randomguys.R
import com.example.randomguys.data.ResourceManager
import com.example.randomguys.presentation.screens.group_edition.predefined_values.PredefinedColorList

data class RouletteItem(
    val name: String,
    val color: Color
) {

    companion object {

        fun fromDto(dto: GroupMemberDto) = RouletteItem(
            name = dto.name,
            color = Color(dto.color.toULong())
        )

        fun create(resourceManager: ResourceManager) = RouletteItem(
            name = resourceManager.stringArray(R.array.predefined_names).random(),
            color = PredefinedColorList.random()
        )
    }
}
