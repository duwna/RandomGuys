package com.example.randomguys.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomguys.data.repositories.SettingsRepository
import com.example.randomguys.navigation.Navigator
import com.example.randomguys.presentation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: SettingsRepository,
    private val navigator: Navigator
) : ViewModel() {

    private val _state = MutableStateFlow(MainViewState(emptyList()))
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
        repository
            .observeSettings()
            .onEach {
                updateState {
                    copy(
                        rouletteRotationDuration = it.rotationDuration,
                        rouletteRotationsCount = it.rotationsCount
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private inline fun updateState(updateAction: MainViewState.() -> MainViewState) {
        _state.value = updateAction.invoke(state.value)
    }
}
