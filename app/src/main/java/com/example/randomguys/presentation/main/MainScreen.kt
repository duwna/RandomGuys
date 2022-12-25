package com.example.randomguys.presentation.main

import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.randomguys.presentation.Screens
import com.example.randomguys.presentation.main.composable_items.AnimatedRoulette

@Composable
fun MainScreen(
    navController: NavController = rememberNavController(),
    viewModel: MainViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Center,
        horizontalAlignment = CenterHorizontally
    ) {

        AnimatedRoulette(
            items = state.rouletteItems,
            initialAngle = state.rouletteRotationAngle,
            onAngleChanged = viewModel::onAngleChanged,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .padding(30.dp)
        )

        Box(modifier = Modifier.height(40.dp)) {
            state.selectedName?.let {
                Text(
                    text = it,
                    fontSize = 36.sp
                )
            }
        }

        Button(onClick = { navController.navigate(Screens.SETTINGS.name) }) {
            Text(text = "open settings")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    MainScreen()
}
