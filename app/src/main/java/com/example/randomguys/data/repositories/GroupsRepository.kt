package com.example.randomguys.data.repositories

import com.example.randomguys.GroupDto
import com.example.randomguys.GroupMemberDto
import com.example.randomguys.data.PersistentStorage
import com.example.randomguys.domain.models.RouletteGroup
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GroupsRepository @Inject constructor(
    private val persistentStorage: PersistentStorage
) {

    fun observeGroups(): Flow<List<RouletteGroup>> =
        persistentStorage.observeGroups().map {
            it.groupsList.map(RouletteGroup.Companion::fromDto)
        }

    suspend fun getGroup(id: String): RouletteGroup = observeGroups()
        .map { it.first { group -> group.id == id } }
        .first()

    suspend fun saveGroup(group: RouletteGroup) {
        val index = observeGroups()
            .map { it.indexOfFirst { groupDto -> groupDto.id == group.id } }
            .first()

        val membersDtoList = group.items.map {
            GroupMemberDto.newBuilder()
                .setName(it.name)
                .setColor(it.color.value.toString())
                .build()
        }

        val groupDto = GroupDto.newBuilder()
            .setId(group.id)
            .addAllMembers(membersDtoList)
            .build()

        persistentStorage.saveGroups {
            when {
                membersDtoList.isEmpty() -> removeGroups(index)
                index < 0 -> addGroups(groupDto)
                else -> setGroups(index, groupDto)
            }
        }
    }
}
