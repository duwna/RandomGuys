package com.example.randomguys.data.proto

import androidx.datastore.core.Serializer
import com.example.randomguys.AllGroupsDto
import java.io.InputStream
import java.io.OutputStream

class GroupsSerializer : Serializer<AllGroupsDto> {

    override val defaultValue: AllGroupsDto = AllGroupsDto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): AllGroupsDto =
        AllGroupsDto.parseFrom(input)

    override suspend fun writeTo(t: AllGroupsDto, output: OutputStream) =
        t.writeTo(output)
}
