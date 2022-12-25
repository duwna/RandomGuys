package com.example.randomguys.ui.main

import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.randomguys.models.RouletteItem
import com.example.randomguys.ui.main.composable_items.AnimatedRoulette

@Composable
fun MainScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Center,
        horizontalAlignment = CenterHorizontally
    ) {
        val items = listOf(
            RouletteItem("AAAA", Color.Blue),
            RouletteItem("ddd", Color.Red),
            RouletteItem("C", Color.Yellow),
            RouletteItem("DD", Color.Green),
            RouletteItem("EEEEE", Color.Blue),
            RouletteItem("DD", Color.Red)
        )

        // TODO move to viewModel
        var selectedItem by remember { mutableStateOf<RouletteItem?>(null) }

        AnimatedRoulette(
            items = items,
            onItemSelected = { selectedItem = it },
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .padding(30.dp)
        )

        Box(modifier = Modifier.height(40.dp)) {
            selectedItem?.name?.let { name ->
                Text(
                    text = name,
                    fontSize = 36.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    MainScreen()
}
