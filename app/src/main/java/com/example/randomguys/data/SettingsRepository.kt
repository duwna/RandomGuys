package com.example.randomguys.data

import androidx.compose.ui.graphics.Color
import com.example.randomguys.AppSettings
import com.example.randomguys.models.RouletteItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
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

    fun observeSettings(): Flow<AppSettings> = persistentStorage.observeSettings()

    suspend fun getDuration() = observeSettings().first().rotationDuration / 100f

    suspend fun getRotation() = observeSettings().first().rotationsCount / 100f

    suspend fun saveDuration(duration: Float) = withContext(Dispatchers.IO) {
        persistentStorage.saveDuration((duration * 100).toInt())
    }

    suspend fun saveRotation(rotation: Float) = withContext(Dispatchers.IO) {
        persistentStorage.saveRotation((rotation * 100).toInt())
    }

}
