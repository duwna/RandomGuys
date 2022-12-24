package com.example.randomguys.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.randomguys.models.RouletteItem

@Composable
fun MainScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        AnimatedRoulette(
            modifier = Modifier.size(350.dp),
            textPositionFraction = 350,
            items = listOf(
                RouletteItem("AAAA", Color.Blue),
                RouletteItem("BBBBBBB", Color.Red),
                RouletteItem("CCC", Color.Yellow)
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    MainScreen()
}
