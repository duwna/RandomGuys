package com.example.randomguys.presentation.screens.settings.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.randomguys.R
import com.example.randomguys.domain.models.RouletteGroup
import com.example.randomguys.domain.models.RouletteItem
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun GroupItem(
    group: RouletteGroup,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onGroupClicked: () -> Unit = {}
) {
    Card(
        modifier,
        border = BorderStroke(
            width = 1.dp,
            color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {

            FlowRow(modifier = Modifier.weight(1f)) {
                group.items.forEach { item ->
                    GroupMember(member = item)
                }
            }

            Image(
                painter = painterResource(id = R.drawable.icon_edit),
                contentDescription = "edit group",
                modifier = Modifier
                    .size(44.dp)
                    .padding(4.dp)
                    .background(color = MaterialTheme.colorScheme.secondary, shape = CircleShape)
                    .padding(8.dp)
                    .clickable(onClick = onGroupClicked)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GroupItemPreview() {
    GroupItem(
        group = RouletteGroup(
            id = "1",
            items = listOf(
                RouletteItem("AAAA", Color.Blue),
                RouletteItem("ddd", Color.Red),
                RouletteItem("C", Color.Yellow),
                RouletteItem("DD", Color.Green),
                RouletteItem("EEEEE", Color.Blue),
                RouletteItem("DD", Color.Red)
            ),
        ),
        isSelected = true,
    )
}
