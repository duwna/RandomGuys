package com.example.randomguys.presentation.screens.group_edition

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.randomguys.presentation.screens.group_edition.composable.MemberInput

@Composable
fun GroupScreen(
    navController: NavController = rememberNavController(),
    viewModel: GroupViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    Column {
        state.group?.items?.forEachIndexed { index, item ->
            MemberInput(
                member = item,
                modifier = Modifier.fillMaxWidth(),
                onTextChanged = { text -> viewModel.onTextChanged(index, text) },
                onRemoveClicked = { viewModel.removeMember(index) }
            )
        }

        Button(onClick = viewModel::addMember) {
            Text(text = "add")
        }
    }
}
