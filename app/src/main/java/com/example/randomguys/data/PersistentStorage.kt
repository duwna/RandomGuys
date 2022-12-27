package com.example.randomguys.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.randomguys.AppSettings
import com.example.randomguys.data.proto.AppAppSettingsSerializer
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersistentStorage @Inject constructor(
    private val context: Context
) {

    companion object {
        private const val APP_SETTINGS_NAME = "app_settings"
        private const val APP_SETTINGS_FILE_NAME = "app_settings.pb"

        private const val DEFAULT_ROTATIONS_COUNT = 10
        private const val DEFAULT_DURATION = 5000
    }

    private val Context.appSettingsStore: DataStore<AppSettings> by dataStore(
        fileName = APP_SETTINGS_FILE_NAME,
        serializer = AppAppSettingsSerializer()
    )

    fun observeSettings(): Flow<AppSettings> = context.appSettingsStore.data

    suspend fun saveDuration(duration: Int) = context.appSettingsStore
        .updateData { it.toBuilder().setRotationDuration(duration).build() }

    suspend fun saveRotation(rotation: Int) = context.appSettingsStore
        .updateData { it.toBuilder().setRotationsCount(rotation).build() }
}
