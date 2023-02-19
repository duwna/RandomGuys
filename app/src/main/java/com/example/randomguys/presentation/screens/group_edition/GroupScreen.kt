package com.example.randomguys.presentation.screens.group_edition

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.randomguys.R
import com.example.randomguys.presentation.screens.group_edition.color_picker.ColorPickerDialog
import com.example.randomguys.presentation.screens.group_edition.composable.MemberInput

@Composable
fun GroupScreen(viewModel: GroupViewModel = hiltViewModel()) {

    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        val items = state.group?.items

        if (!items.isNullOrEmpty()) {
            items.forEachIndexed { index, item ->
                MemberInput(
                    member = item,
                    modifier = Modifier.fillMaxWidth(),
                    onTextChanged = { text -> viewModel.onTextChanged(index, text) },
                    onRemoveClicked = { viewModel.removeMember(index) },
                    onColorClicked = { viewModel.showColorPicker(index) }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = viewModel::addMember
            ) {
                Text(text = stringResource(R.string.add_group_button))
            }

            if (state.isColorPickerVisibleForIndex != null) {
                ColorPickerDialog(onDismiss = viewModel::onColorUpdated)
            }
        }
    }
}
