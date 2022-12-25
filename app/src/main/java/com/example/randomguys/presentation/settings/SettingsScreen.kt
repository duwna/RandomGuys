package com.example.randomguys.presentation.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.randomguys.R
import com.example.randomguys.presentation.settings.composable_items.GroupItem
import com.example.randomguys.presentation.settings.composable_items.SliderWithText

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

        Image(
            painter = painterResource(id = R.drawable.icon_default_roulette_indicator),
            contentDescription = "choose indicator",
            modifier = Modifier
                .size(200.dp)
                .align(CenterHorizontally)
                .background(
                    color = MaterialTheme.colorScheme.onBackground,
                    shape = CircleShape
                )
        )

        Text(
            text = stringResource(id = R.string.settings_groups_title),
            fontSize = 26.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(20.dp)
        )

        state.groups.forEach { group ->
            GroupItem(
                group = group,
                isSelected = group.id == state.selectedGroupId,
                modifier = Modifier
                    .padding(20.dp)
                    .clickable { viewModel.onGroupSelected(group.id) }
            )
        }
    }
}
