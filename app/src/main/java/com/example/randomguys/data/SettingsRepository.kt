package com.example.randomguys.data

import androidx.compose.ui.graphics.Color
import com.example.randomguys.models.RouletteItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository @Inject constructor(
    private val persistentStorage: PersistentStorage
) {
    val items = listOf(
        RouletteItem("DD", Color.Green),
        RouletteItem("EEEEE", Color.Blue),
        RouletteItem("DD", Color.Red)
    )

    fun observeDuration(): Flow<Int> = persistentStorage.observeDuration()
        .flowOn(Dispatchers.IO)
        .filterNotNull()

    fun observeRotation(): Flow<Int> = persistentStorage.observeRotation()
        .flowOn(Dispatchers.IO)
        .filterNotNull()

    suspend fun getDuration() = observeDuration().first() / 100f

    suspend fun getRotation() = observeRotation().first() / 100f

    suspend fun saveDuration(duration: Float) = withContext(Dispatchers.IO) {
        persistentStorage.saveDuration((duration * 100).toInt())
    }

    suspend fun saveRotation(rotation: Float) = withContext(Dispatchers.IO) {
        persistentStorage.saveRotation((rotation * 100).toInt())
    }

}
