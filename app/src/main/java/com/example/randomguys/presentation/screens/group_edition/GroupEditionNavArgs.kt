package com.example.randomguys.presentation.screens.group_edition

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.randomguys.presentation.screens.group_edition.GroupEditionNavArgs.ArgKeys.ARG_GROUP_ID

data class GroupEditionNavArgs(
    val groupId: String? = null
) {

    constructor(savedStateHandle: SavedStateHandle) : this(
        groupId = savedStateHandle[ARG_GROUP_ID]
    )

    fun createLink(route: String): String = route
        .replace("{$ARG_GROUP_ID}", groupId.toString())

    companion object {
        const val ROUTE_PARAMS = "?$ARG_GROUP_ID={$ARG_GROUP_ID}"

        fun getNamedNavArgs() = listOf(
            navArgument(ARG_GROUP_ID) {
                type = NavType.StringType
                nullable = true
            }
        )
    }

    private object ArgKeys {
        const val ARG_GROUP_ID = "arg_group_id"
    }
}
