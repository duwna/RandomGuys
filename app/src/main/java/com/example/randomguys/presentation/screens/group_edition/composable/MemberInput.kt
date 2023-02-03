package com.example.randomguys.presentation.screens.group_edition.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.randomguys.R
import com.example.randomguys.domain.models.RouletteItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemberInput(
    member: RouletteItem,
    modifier: Modifier,
    onTextChanged: (String) -> Unit,
    onRemoveClicked: () -> Unit
) {
    Row(modifier) {
        TextField(
            modifier = Modifier.weight(1f),
            value = member.name,
            onValueChange = onTextChanged,
            trailingIcon = {
                Image(
                    painter = painterResource(R.drawable.icon_remove),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                    contentDescription = "remove member",
                    modifier = Modifier.clickable(onClick = onRemoveClicked)
                )
            }
        )

        Spacer(modifier = Modifier.width(16.dp))

        Spacer(
            modifier = Modifier
                .background(color = member.color, shape = CircleShape)
                .align(CenterVertically)
                .size(40.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MemberInputPreview() {

}
