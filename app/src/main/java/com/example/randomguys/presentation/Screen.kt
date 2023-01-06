package com.example.randomguys.presentation

import com.example.randomguys.presentation.screens.group_edition.GroupEditionNavArgs

sealed class Screen(val route: String) {

    object MAIN : Screen("main")
    object SETTINGS : Screen("settings")

    object GROUP : Screen("group" + GroupEditionNavArgs.ROUTE_PARAMS) {
        fun withArgs(args: GroupEditionNavArgs): String = args.createLink(route)
    }
}
