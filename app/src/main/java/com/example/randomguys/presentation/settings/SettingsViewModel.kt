package com.example.randomguys.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomguys.data.SettingsRepository
import com.example.randomguys.models.RouletteGroup
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: SettingsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsViewState())
    val state = _state.asStateFlow()

    init {
        setInitialData()
    }

    fun onDurationChanged(duration: Float) {
        updateState { copy(selectedDuration = duration) }
    }

    fun saveDuration() {
        viewModelScope.launch {
            repository.saveDuration(state.value.selectedDuration)
        }
    }

    fun onRotationsCountChanged(duration: Float) {
        updateState { copy(selectedRotation = duration) }
    }

    fun saveRotationsCount() {
        viewModelScope.launch {
            repository.saveRotation(state.value.selectedRotation)
        }
    }

    fun onGroupSelected(id: Int) {
        updateState { copy(selectedGroupId = id) }
    }

    private fun setInitialData() {
        viewModelScope.launch {
            updateState {
                copy(
                    selectedDuration = repository.getDuration(),
                    selectedRotation = repository.getRotation(),
                    groups = listOf(
                        RouletteGroup(id = 0, items = repository.items),
                        RouletteGroup(id = 1, items = repository.items)
                    )
                )
            }
        }
    }

    private inline fun updateState(updateAction: SettingsViewState.() -> SettingsViewState) {
        _state.value = updateAction.invoke(state.value)
    }
}
