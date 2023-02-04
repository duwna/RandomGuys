package com.example.randomguys.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomguys.data.repositories.GroupsRepository
import com.example.randomguys.data.repositories.SettingsRepository
import com.example.randomguys.navigation.Navigator
import com.example.randomguys.presentation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val groupsRepository: GroupsRepository,
    private val navigator: Navigator
) : ViewModel() {

    private val _state = MutableStateFlow(MainViewState())
    val state = _state.asStateFlow()

    init {
        observeSettingsChanges()
    }

    fun onAngleChanged(rotationAngle: Int?) {
        updateState { copy(rouletteRotationAngle = rotationAngle) }
    }

    fun navigateToSettings() {
        navigator.navigate(Screen.SETTINGS)
    }

    private fun observeSettingsChanges() {
        settingsRepository
            .observeSettings()
            .combine(groupsRepository.observeGroups()) { settings, groups ->
                updateState {
                    copy(
                        rouletteItems = groups.find { it.id == settings.selectedGroupId }?.items.orEmpty(),
                        rouletteRotationDuration = settings.rotationDuration,
                        rouletteRotationsCount = settings.rotationsCount
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private inline fun updateState(updateAction: MainViewState.() -> MainViewState) {
        _state.value = updateAction.invoke(state.value)
    }
}
