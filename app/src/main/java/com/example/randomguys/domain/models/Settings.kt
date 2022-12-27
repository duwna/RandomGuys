package com.example.randomguys.domain.models

import com.example.randomguys.AppSettingsDto

data class Settings(
    val rotationDuration: Int,
    val rotationsCount: Int
) {

    companion object {

        fun fromDto(dto: AppSettingsDto) = Settings(
            rotationDuration = dto.rotationDuration,
            rotationsCount = dto.rotationsCount
        )
    }
}
