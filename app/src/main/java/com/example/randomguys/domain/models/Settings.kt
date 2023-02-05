package com.example.randomguys.domain.models

import com.example.randomguys.AppSettingsDto

data class Settings(
    val rotationDuration: Int,
    val rotationsCount: Int,
    val selectedGroupId: String
) {

    companion object {

        const val DEFAULT_ROTATION_DURATION_SECONDS = 3
        const val DEFAULT_ROTATIONS_COUNT = 10

        val rotationDurationRange = 1..10
        val rotationsCountRange = 1..50

        fun fromDto(dto: AppSettingsDto) = Settings(
            rotationDuration = dto.rotationDuration.takeIf { it != 0 } ?: DEFAULT_ROTATION_DURATION_SECONDS,
            rotationsCount = dto.rotationsCount.takeIf { it != 0 } ?: DEFAULT_ROTATIONS_COUNT,
            selectedGroupId = dto.selectedGroupId
        )
    }
}
