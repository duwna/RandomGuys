package com.example.randomguys.presentation.screens.settings

import android.os.VibrationEffect
import android.os.Vibrator
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomguys.data.MessageHandler
import com.example.randomguys.data.launchHandlingErrors
import com.example.randomguys.data.messageHandler
import com.example.randomguys.data.repositories.GroupsRepository
import com.example.randomguys.data.repositories.SettingsRepository
import com.example.randomguys.navigation.Navigator
import com.example.randomguys.presentation.Screen
import com.example.randomguys.presentation.screens.group_edition.GroupEditionNavArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val groupsRepository: GroupsRepository,
    private val vibrator: Vibrator,
    private val navigator: Navigator,
    private val messageHandler: MessageHandler
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsViewState())
    val state = _state.asStateFlow()

    private val sliderVibrationEffect = VibrationEffect.createOneShot(1, 50)

    init {
        setInitialData()
        observeGroupsChanges()
    }

    fun onDurationChanged(duration: Float) {
        val oldDuration = state.value.selectedDuration
        updateState { copy(selectedDuration = duration) }
        vibrateIfValueChanged(oldDuration, duration)
    }

    fun saveDuration() {
        viewModelScope.launchHandlingErrors(messageHandler) {
            settingsRepository.saveDuration(state.value.selectedDuration)
        }
    }

    fun onRotationsCountChanged(rotation: Float) {
        val oldRotation = state.value.selectedRotation
        updateState { copy(selectedRotation = rotation) }
        vibrateIfValueChanged(oldRotation, rotation)
    }

    fun saveRotationsCount() {
        viewModelScope.launchHandlingErrors(messageHandler) {
            settingsRepository.saveRotation(state.value.selectedRotation)
        }
    }

    fun onGroupSelected(id: String) {
        updateState { copy(selectedGroupId = id) }

        viewModelScope.launchHandlingErrors(messageHandler) {
            settingsRepository.saveSelectedGroupId(id)
        }
    }

    fun navigateToGroup(id: String? = null) {
        if (id != null) {
            navigator.navigate(Screen.GROUP.withArgs(GroupEditionNavArgs(id)))
        } else {
            navigator.navigate(Screen.GROUP.route)
        }
    }

    private fun setInitialData() {
        viewModelScope.launchHandlingErrors(messageHandler) {
            val settings = settingsRepository.observeSettings().first()
            updateState {
                copy(
                    selectedDuration = settings.rotationDuration / 100f,
                    selectedRotation = settings.rotationsCount / 100f,
                    selectedGroupId = settings.selectedGroupId,
                    groups = groupsRepository.observeGroups().first()
                )
            }
        }
    }

    private fun observeGroupsChanges() {
        groupsRepository
            .observeGroups()
            .onEach { updateState { copy(groups = it) } }
            .launchIn(viewModelScope + messageHandler(messageHandler))
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
