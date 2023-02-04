package com.example.randomguys.data.repositories

import com.example.randomguys.data.PersistentStorage
import com.example.randomguys.domain.models.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository @Inject constructor(
    private val persistentStorage: PersistentStorage
) {

    fun observeSettings(): Flow<Settings> = persistentStorage.observeSettings()
        .map(Settings.Companion::fromDto)

    suspend fun saveDuration(duration: Float) = persistentStorage.saveSettings {
        setRotationDuration((duration * 100).toInt())
    }

    suspend fun saveRotation(rotation: Float) = persistentStorage.saveSettings {
        setRotationsCount((rotation * 100).toInt())
    }

    suspend fun saveSelectedGroupId(id: String) = persistentStorage.saveSettings {
        setSelectedGroupId(id)
    }
}
