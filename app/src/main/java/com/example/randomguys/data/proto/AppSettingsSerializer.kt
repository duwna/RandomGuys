package com.example.randomguys.data.proto

import androidx.datastore.core.Serializer
import com.example.randomguys.AppSettings
import java.io.InputStream
import java.io.OutputStream

class AppAppSettingsSerializer : Serializer<AppSettings> {

    override val defaultValue: AppSettings = AppSettings.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): AppSettings =
        AppSettings.parseFrom(input)

    override suspend fun writeTo(t: AppSettings, output: OutputStream) =
        t.writeTo(output)
}
