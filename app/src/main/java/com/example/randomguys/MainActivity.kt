package com.example.randomguys

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.randomguys.data.MessageHandler
import com.example.randomguys.presentation.Screen
import com.example.randomguys.presentation.SnackbarScaffold
import com.example.randomguys.presentation.screens.group_edition.GroupEditionNavArgs
import com.example.randomguys.presentation.screens.group_edition.GroupScreen
import com.example.randomguys.presentation.screens.main.MainScreen
import com.example.randomguys.presentation.screens.settings.SettingsScreen
import com.example.randomguys.presentation.theme.RandomGuysTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var messageHandler: MessageHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RandomGuysTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    SnackbarScaffold(messageHandler) {
                        MainNavHost(navController = rememberNavController())
                    }
                }
            }
        }
    }

    @Composable
    private fun MainNavHost(navController: NavHostController) {
        NavHost(navController, startDestination = Screen.SETTINGS.route) {
            composable(Screen.MAIN.route) {
                MainScreen(navController)
            }
            composable(Screen.SETTINGS.route) {
                SettingsScreen(navController)
            }
            composable(Screen.GROUP.route, GroupEditionNavArgs.getNamedNavArgs()) {
                GroupScreen(navController)
            }
        }
    }
}
