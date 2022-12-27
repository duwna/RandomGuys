package com.example.randomguys.data.repositories

import androidx.compose.ui.graphics.Color
import com.example.randomguys.data.PersistentStorage
import com.example.randomguys.domain.models.RouletteGroup
import com.example.randomguys.domain.models.RouletteItem
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GroupsRepository @Inject constructor(
    private val persistentStorage: PersistentStorage
) {

    private val items = listOf(
        RouletteItem("DD", Color.Green),
        RouletteItem("EEEEE", Color.Blue),
        RouletteItem("DD", Color.Red)
    )

    suspend fun getGroup(id: Int): RouletteGroup? {
        return getGroups().first { it.id == id }
    }

    suspend fun getGroups(): List<RouletteGroup> {
        delay(200)

        return listOf(
            RouletteGroup(0, items),
            RouletteGroup(1, items)
        )
    }
}
