package com.example.randomguys.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersistentStorage @Inject constructor(
    private val context: Context
) {

    companion object {
        private const val DATA_STORE_NAME = "settings"

        private const val DEFAULT_ROTATIONS_COUNT = 10
        private const val DEFAULT_DURATION = 5000
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(DATA_STORE_NAME)

    private val durationKey = intPreferencesKey("DURATION_KEY")
    private val rotationKey = intPreferencesKey("ROTATION_KEY")

    fun observeDuration(): Flow<Int> = observeChanges(durationKey, DEFAULT_DURATION)

    fun observeRotation(): Flow<Int> = observeChanges(rotationKey, DEFAULT_ROTATIONS_COUNT)

    suspend fun saveDuration(duration: Int) = updateValue(durationKey, duration)

    suspend fun saveRotation(rotation: Int) = updateValue(rotationKey, rotation)

    private fun <T> observeChanges(key: Preferences.Key<T>, defaultValue: T): Flow<T> =
        context.dataStore.data.map { it[key] ?: defaultValue }

    private suspend fun <T> updateValue(key: Preferences.Key<T>, value: T) =
        context.dataStore.edit { it[key] = value }
}
