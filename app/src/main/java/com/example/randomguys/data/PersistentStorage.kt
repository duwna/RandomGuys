package com.example.randomguys.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.randomguys.AllGroupsDto
import com.example.randomguys.AppSettingsDto
import com.example.randomguys.data.proto.AppSettingsSerializer
import com.example.randomguys.data.proto.GroupsSerializer
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

class PersistentStorage @Inject constructor(
    private val context: Context
) {

    companion object {
        private const val APP_SETTINGS_FILE_NAME = "app_settings.pb"
        private const val GROUPS_FILE_NAME = "groups.pb"
    }

    private val Context.appSettingsStore: DataStore<AppSettingsDto> by dataStore(
        fileName = APP_SETTINGS_FILE_NAME,
        serializer = AppSettingsSerializer()
    )

    private val Context.groupsStore: DataStore<AllGroupsDto> by dataStore(
        fileName = GROUPS_FILE_NAME,
        serializer = GroupsSerializer()
    )

    fun observeSettings(): Flow<AppSettingsDto> = context.appSettingsStore.data

    suspend fun saveSettings(transform: AppSettingsDto.Builder.() -> AppSettingsDto.Builder) =
        context.appSettingsStore.updateData { transform.invoke(it.toBuilder()).build() }

    fun observeGroups(): Flow<AllGroupsDto> = context.groupsStore.data

    suspend fun saveGroups(transform: AllGroupsDto.Builder.() -> AllGroupsDto.Builder) =
        context.groupsStore.updateData { transform.invoke(it.toBuilder()).build() }

}
