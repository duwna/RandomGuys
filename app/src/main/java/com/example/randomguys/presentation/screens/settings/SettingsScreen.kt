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
import com.example.randomguys.R
import com.example.randomguys.presentation.screens.settings.composable.InfiniteRotatingIndicator
import com.example.randomguys.presentation.screens.settings.composable.GroupItem
import com.example.randomguys.presentation.screens.settings.composable.SliderWithText

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {

    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

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
                    onGroupClicked = { viewModel.navigateToGroup(group.id) },
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
            onClick = viewModel::navigateToGroup
        )
        {
            Text(text = stringResource(R.string.add_group_button))
        }

        Text(
            text = stringResource(id = R.string.settings_indicator_title),
            fontSize = 26.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(20.dp)
        )

        InfiniteRotatingIndicator(modifier = Modifier.align(CenterHorizontally))

        SliderWithText(
            selectedText = stringResource(id = R.string.settings_duration_title, state.rotationDuration),
            selectedValue = state.durationSliderValue,
            onValueChanged = viewModel::onDurationChanged,
            onValueChangeFinished = viewModel::saveDuration
        )

        SliderWithText(
            selectedText = stringResource(id = R.string.settings_rotation_title, state.rotationsCount),
            selectedValue = state.rotationsCountSliderValue,
            onValueChanged = viewModel::onRotationsCountChanged,
            onValueChangeFinished = viewModel::saveRotationsCount
        )
    }
}
