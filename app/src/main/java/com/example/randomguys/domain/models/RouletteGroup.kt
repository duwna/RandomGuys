package com.example.randomguys.domain.models

import com.example.randomguys.GroupDto
import com.example.randomguys.data.ResourceManager
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

        fun create(resourceManager: ResourceManager, itemsSize: Int = 3) = RouletteGroup(
            id = UUID.randomUUID().toString(),
            items = List(itemsSize) { RouletteItem.create(resourceManager) }
        )
    }
}
