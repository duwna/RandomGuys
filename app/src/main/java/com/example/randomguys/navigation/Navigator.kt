package com.example.randomguys.navigation

import androidx.navigation.NavController
import com.example.randomguys.presentation.Screen
import javax.inject.Inject

interface Navigator {

    fun setController(navController: NavController)

    fun navigate(screen: Screen)

    fun navigate(route: String)

    fun popBackStack()

    fun clear()

}

class NavigatorImpl @Inject constructor() : Navigator {

    private var navController: NavController? = null

    override fun setController(navController: NavController) {
        this.navController = navController
    }

    override fun navigate(screen: Screen) {
        navController?.navigate(screen.route)
    }

    override fun navigate(route: String) {
        navController?.navigate(route)
    }

    override fun popBackStack() {
        navController?.popBackStack()
    }

    override fun clear() {
        navController = null
    }
}
