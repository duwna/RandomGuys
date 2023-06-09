package com.example.randomguys.presentation.screens.settings.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SliderWithText(
    selectedText: String,
    selectedValue: Float,
    onValueChanged: (Float) -> Unit,
    onValueChangeFinished: () -> Unit
) {
    Text(
        text = selectedText,
        color = MaterialTheme.colorScheme.onBackground,
        fontSize = 26.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(top = 20.dp, start = 20.dp)
    )

    Slider(
        value = selectedValue,
        onValueChange = onValueChanged,
        onValueChangeFinished = onValueChangeFinished,
        modifier = Modifier.padding(horizontal = 14.dp)
    )
}
