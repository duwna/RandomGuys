package com.example.randomguys.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.randomguys.AppSettingsDto
import com.example.randomguys.data.proto.AppAppSettingsSerializer
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersistentStorage @Inject constructor(
    private val context: Context
) {

    companion object {
        private const val APP_SETTINGS_FILE_NAME = "app_settings.pb"
    }

    private val Context.appSettingsStore: DataStore<AppSettingsDto> by dataStore(
        fileName = APP_SETTINGS_FILE_NAME,
        serializer = AppAppSettingsSerializer()
    )

    fun observeSettings(): Flow<AppSettingsDto> = context.appSettingsStore.data

    suspend fun saveDuration(duration: Int) = context.appSettingsStore
        .updateData { it.toBuilder().setRotationDuration(duration).build() }

    suspend fun saveRotation(rotation: Int) = context.appSettingsStore
        .updateData { it.toBuilder().setRotationsCount(rotation).build() }
}
