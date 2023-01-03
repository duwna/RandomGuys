package com.example.randomguys.presentation.screens.settings

import android.os.VibrationEffect
import android.os.Vibrator
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomguys.data.repositories.GroupsRepository
import com.example.randomguys.data.repositories.SettingsRepository
import com.example.randomguys.domain.models.RouletteGroup
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val groupsRepository: GroupsRepository,
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
            settingsRepository.saveDuration(state.value.selectedDuration)
        }
    }

    fun onRotationsCountChanged(rotation: Float) {
        val oldRotation = state.value.selectedRotation
        updateState { copy(selectedRotation = rotation) }
        vibrateIfValueChanged(oldRotation, rotation)
    }

    fun saveRotationsCount() {
        viewModelScope.launch {
            settingsRepository.saveRotation(state.value.selectedRotation)
        }
    }

    fun onGroupSelected(id: Int) {
        updateState { copy(selectedGroupId = id) }
    }

    private fun setInitialData() {
        viewModelScope.launch {
            updateState {
                copy(
                    selectedDuration = settingsRepository.getDuration(),
                    selectedRotation = settingsRepository.getRotation(),
                    groups = groupsRepository.getGroups()
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