@file:OptIn(ExperimentalComposeUiApi::class)

package com.example.randomguys.presentation.screens.main.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.randomguys.domain.models.RouletteItem
import com.example.randomguys.presentation.screens.settings.composable.GroupMember

@Composable
fun AutoRouletteSuccessDialog(
    rouletteItems: List<RouletteItem>,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {

        Card(elevation = cardElevation(16.dp)) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {

                rouletteItems.forEach { item ->
                    GroupMember(member = item)
                }
            }
        }
    }
}
