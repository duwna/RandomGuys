@file:OptIn(ExperimentalComposeUiApi::class)

package com.example.randomguys.presentation.screens.group_edition.color_picker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.randomguys.presentation.screens.group_edition.predefined_values.PredefinedColors

@Composable
fun ColorPickerDialog(
    onDismiss: (Color?) -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss.invoke(null) },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {

        val colorRows = remember {
            val itemsInRow = 7
            PredefinedColors.list.shuffled().chunked(itemsInRow)
        }

        Card(elevation = cardElevation(16.dp)) {

            Column(modifier = Modifier.padding(16.dp)) {
                colorRows.forEach { colorRaw ->
                    Row {
                        colorRaw.forEach { color ->
                            Spacer(
                                modifier = Modifier
                                    .size(40.dp)
                                    .padding(4.dp)
                                    .background(color = color, shape = CircleShape)
                                    .clip(CircleShape)
                                    .clickable(onClick = { onDismiss.invoke(color) })
                            )
                        }
                    }
                }
            }
        }
    }
}
