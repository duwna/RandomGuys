package com.example.randomguys.presentation.screens.group_edition.color_picker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun ColorPickerDialog(
    onDismiss: (Color?) -> Unit
) {
    Dialog(onDismissRequest = { onDismiss.invoke(null) }) {

        val colors = PredefinedColors.list
        val itemsInRow = 7



        Card(
            modifier = Modifier.padding(16.dp),
            elevation = cardElevation(16.dp)
        ) {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(horizontal = 16.dp)
                    .align(CenterHorizontally)
                    .verticalScroll(rememberScrollState())
            ) {

                colors.forEach { color ->
                    Spacer(
                        modifier = Modifier
                            .size(40.dp)
                            .padding(4.dp)
                            .background(color = color, shape = CircleShape)
                            .clickable(onClick = { onDismiss.invoke(color) })
                    )
                }
            }
        }
    }
}
