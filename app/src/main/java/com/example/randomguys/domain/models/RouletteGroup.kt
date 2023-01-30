package com.example.randomguys.domain.models

import com.example.randomguys.GroupDto
import java.util.UUID

data class RouletteGroup(
    val id: String,
    val items: List<RouletteItem>
) {

    companion object {

        fun fromDto(dto: GroupDto) = RouletteGroup(
            id = dto.id,
            items = dto.membersList.map(RouletteItem.Companion::fromDto)
        )

        fun create() = RouletteGroup(
            id = UUID.randomUUID().toString(),
            items = emptyList()
        )
    }
}
