package com.example.randomguys.presentation.screens.group_edition.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
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
            value = member.name,
            onValueChange = onTextChanged,
            trailingIcon = {
                Image(
                    painter = painterResource(R.drawable.icon_remove),
                    contentDescription = "remove member",
                    modifier = Modifier.clickable(onClick = onRemoveClicked)
                )
            }
        )

    }
}

@Preview(showBackground = true)
@Composable
private fun MemberInputPreview() {

}
