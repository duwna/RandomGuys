package com.example.randomguys.models

data class AppSettingsKotlin(
    val rotationDuration: Int,
    val rotationsCount: Int
) {

    companion object {

        fun getDefaultInstance() = AppSettingsKotlin(
            rotationDuration = 5000,
            rotationsCount = 10
        )
    }
}
