package com.example.randomguys.presentation.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.randomguys.R
import com.example.randomguys.presentation.Screen
import com.example.randomguys.presentation.screens.group_edition.GroupEditionNavArgs
import com.example.randomguys.presentation.screens.group_edition.color_picker.ColorPickerDialog
import com.example.randomguys.presentation.screens.settings.composable.AnimatedIndicator
import com.example.randomguys.presentation.screens.settings.composable.GroupItem
import com.example.randomguys.presentation.screens.settings.composable.SliderWithText

@Composable
fun SettingsScreen(
    navController: NavController = rememberNavController(),
    viewModel: SettingsViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        SliderWithText(
            selectedText = stringResource(id = R.string.settings_duration_title, state.durationText),
            selectedValue = state.selectedDuration,
            onValueChanged = viewModel::onDurationChanged,
            onValueChangeFinished = viewModel::saveDuration
        )

        SliderWithText(
            selectedText = stringResource(id = R.string.settings_rotation_title, state.rotationText),
            selectedValue = state.selectedRotation,
            onValueChanged = viewModel::onRotationsCountChanged,
            onValueChangeFinished = viewModel::saveRotationsCount
        )

        Text(
            text = stringResource(id = R.string.settings_indicator_title),
            fontSize = 26.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(20.dp)
        )

        AnimatedIndicator(modifier = Modifier.align(CenterHorizontally))

        Text(
            text = stringResource(id = R.string.settings_groups_title),
            fontSize = 26.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(20.dp)
        )

        state.groups.forEach { group ->
            key(group.id) {
                GroupItem(
                    group = group,
                    isSelected = group.id == state.selectedGroupId,
                    onGroupClicked = { navController.navigate(Screen.GROUP.withArgs(GroupEditionNavArgs(group.id))) },
                    modifier = Modifier
                        .padding(20.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { viewModel.onGroupSelected(group.id) }
                )
            }
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            onClick = { navController.navigate(Screen.GROUP.route) })
        {
            Text(text = stringResource(R.string.add_group_button))
        }
    }
}
