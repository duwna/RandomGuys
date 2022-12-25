package com.example.randomguys.presentation.settings.composable_items

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.randomguys.models.RouletteGroup
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun GroupItem(
    group: RouletteGroup,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier.border(
            width = if (isSelected) 1.dp else 0.dp,
            color = MaterialTheme.colorScheme.primary,
            shape = RoundedCornerShape(12.dp)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        FlowRow(modifier = Modifier.fillMaxSize()) {
            group.items.forEach { item ->
                Row(modifier = Modifier.padding(8.dp)) {

                    Spacer(
                        modifier = Modifier
                            .size(20.dp)
                            .background(color = item.color, shape = CircleShape)
                    )

                    Text(
                        text = item.name,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}

