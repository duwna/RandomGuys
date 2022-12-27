package com.example.randomguys.data.repositories

import com.example.randomguys.data.PersistentStorage
import com.example.randomguys.domain.models.Settings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository @Inject constructor(
    private val persistentStorage: PersistentStorage
) {

    fun observeSettings(): Flow<Settings> = persistentStorage.observeSettings()
        .map(Settings.Companion::fromDto)

    suspend fun getDuration() = observeSettings().first().rotationDuration / 100f

    suspend fun getRotation() = observeSettings().first().rotationsCount / 100f

    suspend fun saveDuration(duration: Float) = withContext(Dispatchers.IO) {
        persistentStorage.saveDuration((duration * 100).toInt())
    }

    suspend fun saveRotation(rotation: Float) = withContext(Dispatchers.IO) {
        persistentStorage.saveRotation((rotation * 100).toInt())
    }

}
