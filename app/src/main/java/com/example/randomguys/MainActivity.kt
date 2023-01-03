package com.example.randomguys

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.randomguys.presentation.Screens
import com.example.randomguys.presentation.screens.group_edition.GroupScreen
import com.example.randomguys.presentation.screens.main.MainScreen
import com.example.randomguys.presentation.screens.settings.SettingsScreen
import com.example.randomguys.presentation.theme.RandomGuysTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RandomGuysTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {

                    val navController = rememberNavController()

                    NavHost(navController, startDestination = Screens.SETTINGS.name) {
                        composable(Screens.MAIN.name) { MainScreen(navController) }
                        composable(Screens.SETTINGS.name) { SettingsScreen(navController) }
                        composable(Screens.GROUP.name) { GroupScreen(navController) }
                    }
                }
            }
        }
    }
}
