package com.example.randomguys.presentation.settings

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun SettingsScreen(navController: NavController) {
    Button(onClick = { navController.popBackStack() }) {
        Text(text = "exit")
    }
}
