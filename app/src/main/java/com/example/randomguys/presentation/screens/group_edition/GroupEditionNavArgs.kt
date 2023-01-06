package com.example.randomguys.presentation.screens.group_edition

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.randomguys.presentation.screens.group_edition.GroupEditionNavArgs.ArgKeys.ARG_GROUP_ID

data class GroupEditionNavArgs(
    val groupId: Int? = null
) {

    constructor(savedStateHandle: SavedStateHandle) : this(
        groupId = savedStateHandle.get<String?>(ARG_GROUP_ID)?.toInt()
    )

    fun createLink(route: String): String = route
        .replace("{$ARG_GROUP_ID}", groupId.toString())

    companion object {
        const val ROUTE_PARAMS = "?$ARG_GROUP_ID={$ARG_GROUP_ID}"

        fun getNamedNavArgs() = listOf(
            navArgument(ARG_GROUP_ID) {
                type = NavType.StringType // IntType does not support nullable
                nullable = true
            }
        )
    }

    private object ArgKeys {
        const val ARG_GROUP_ID = "arg_group_id"
    }
}
