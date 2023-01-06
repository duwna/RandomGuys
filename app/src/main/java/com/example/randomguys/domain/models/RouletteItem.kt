package com.example.randomguys.domain.models

import androidx.compose.ui.graphics.Color
import com.example.randomguys.GroupMemberDto

data class RouletteItem(
    val name: String,
    val color: Color
) {

    companion object {

        fun fromDto(dto: GroupMemberDto) = RouletteItem(
            name = dto.name,
            color = Color(dto.color)
        )
    }
}
