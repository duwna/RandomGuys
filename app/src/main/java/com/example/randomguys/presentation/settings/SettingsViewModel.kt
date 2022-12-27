package com.example.randomguys.presentation.settings

import android.os.VibrationEffect
import android.os.Vibrator
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
    private val repository: SettingsRepository,
    private val vibrator: Vibrator
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsViewState())
    val state = _state.asStateFlow()

    private val sliderVibrationEffect = VibrationEffect.createOneShot(1, 50)

    init {
        setInitialData()
    }

    fun onDurationChanged(duration: Float) {
        val oldDuration = state.value.selectedDuration
        updateState { copy(selectedDuration = duration) }
        vibrateIfValueChanged(oldDuration, duration)
    }

    fun saveDuration() {
        viewModelScope.launch {
            repository.saveDuration(state.value.selectedDuration)
        }
    }

    fun onRotationsCountChanged(rotation: Float) {
        val oldRotation = state.value.selectedRotation
        updateState { copy(selectedRotation = rotation) }
        vibrateIfValueChanged(oldRotation, rotation)
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

    private fun vibrateIfValueChanged(oldValue: Float, newValue: Float) {
        if ((oldValue * 100).toInt() != (newValue * 100).toInt()) {
            vibrator.vibrate(sliderVibrationEffect)
        }
    }

    private inline fun updateState(updateAction: SettingsViewState.() -> SettingsViewState) {
        _state.value = updateAction.invoke(state.value)
    }
}
