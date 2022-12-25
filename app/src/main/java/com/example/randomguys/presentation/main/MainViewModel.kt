package com.example.randomguys.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomguys.data.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: SettingsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MainViewState(repository.items))
    val state = _state.asStateFlow()

    init {
        observeSettingsChanges()
    }

    fun onAngleChanged(rotationAngle: Int?) {
        updateState { copy(rouletteRotationAngle = rotationAngle) }
    }

    private fun observeSettingsChanges() {
        repository
            .observeDuration()
            .onEach { updateState { copy(rouletteRotationDuration = it) } }
            .launchIn(viewModelScope)

        repository
            .observeRotation()
            .onEach { updateState { copy(rouletteRotationsCount = it) } }
            .launchIn(viewModelScope)
    }

    private inline fun updateState(updateAction: MainViewState.() -> MainViewState) {
        _state.value = updateAction.invoke(state.value)
    }
}
