package com.example.randomguys.presentation.screens.settings.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.randomguys.domain.models.RouletteItem

@Composable
fun GroupMember(
    member: RouletteItem,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {

        Spacer(
            modifier = Modifier
                .size(20.dp)
                .background(color = member.color, shape = CircleShape)
        )

        Text(
            text = member.name,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}
