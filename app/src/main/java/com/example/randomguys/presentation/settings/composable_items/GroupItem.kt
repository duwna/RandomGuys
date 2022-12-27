package com.example.randomguys.presentation.settings.composable_items

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
        modifier,
        border = BorderStroke(
            width = 1.dp,
            color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        FlowRow(modifier = Modifier.fillMaxSize()) {
            group.items.forEach { item ->
                GroupMember(member = item, Modifier.padding(8.dp))
            }
        }
    }
}

