package com.example.randomguys.domain.models

import com.example.randomguys.GroupDto

data class RouletteGroup(
    val id: Int,
    val items: List<RouletteItem>
) {

    companion object {

        fun fromDto(dto: GroupDto) = RouletteGroup(
            id = dto.id,
            items = dto.membersList.map(RouletteItem.Companion::fromDto)
        )
    }
}
