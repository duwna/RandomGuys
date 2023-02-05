package com.example.randomguys.presentation.screens.settings.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.randomguys.domain.models.RouletteItem

@Composable
fun GroupMember(
    member: RouletteItem,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(6.dp)
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = CircleShape
            )
            .padding(vertical = 6.dp, horizontal = 8.dp)
    ) {

        Spacer(
            modifier = Modifier
                .size(20.dp)
                .background(color = member.color, shape = CircleShape)
                .align(CenterVertically)
        )

        Text(
            text = member.name,
            modifier = Modifier
                .padding(start = 8.dp)
                .align(CenterVertically)
        )
    }
}
