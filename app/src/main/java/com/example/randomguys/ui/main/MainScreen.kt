package com.example.randomguys.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.randomguys.models.RouletteItem
import com.example.randomguys.ui.main.composable_items.AnimatedRoulette

@Composable
fun MainScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        AnimatedRoulette(
            items = listOf(
                RouletteItem("AAAA", Color.Blue),
                RouletteItem("BBBBBBB", Color.Red),
                RouletteItem("CCC", Color.Yellow)
            ),
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)
                .padding(30.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    MainScreen()
}
