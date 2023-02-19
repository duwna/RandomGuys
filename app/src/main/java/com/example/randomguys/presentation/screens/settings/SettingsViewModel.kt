package com.example.randomguys.presentation.screens.settings

import android.os.VibrationEffect
import android.os.Vibrator
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomguys.data.MessageHandler
import com.example.randomguys.data.exceptionHandler
import com.example.randomguys.data.repositories.GroupsRepository
import com.example.randomguys.data.repositories.SettingsRepository
import com.example.randomguys.domain.models.Settings.Companion.rotationDurationRange
import com.example.randomguys.domain.models.Settings.Companion.rotationsCountRange
import com.example.randomguys.navigation.Navigator
import com.example.randomguys.presentation.Screen
import com.example.randomguys.presentation.screens.group_edition.GroupEditionNavArgs
import com.example.randomguys.presentation.utils.SliderFractionUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
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

    fun onRotationsCountChanged(rotationFraction: Float) {
        val oldRotationCount = state.value.rotationsCount
        val newRotationsCount = SliderFractionUtils.getValueFromFraction(rotationsCountRange, rotationFraction)
        updateState { copy(rotationsCount = newRotationsCount) }
        if (oldRotationCount != newRotationsCount) vibrator.vibrate(sliderVibrationEffect)
    }

    fun onDurationChanged(durationFraction: Float) {
        val oldDuration = state.value.rotationDuration
        val newDuration = SliderFractionUtils.getValueFromFraction(rotationDurationRange, durationFraction)
        updateState { copy(rotationDuration = newDuration) }
        if (oldDuration != newDuration) vibrator.vibrate(sliderVibrationEffect)
    }

    fun saveDuration() {
        viewModelScope.launch(exceptionHandler(messageHandler)) {
            settingsRepository.saveDuration(state.value.rotationDuration)
        }
    }

    fun saveRotationsCount() {
        viewModelScope.launch(exceptionHandler(messageHandler)) {
            settingsRepository.saveRotationsCount(state.value.rotationsCount)
        }
    }

    fun onGroupSelected(id: String) {
        updateState { copy(selectedGroupId = id) }

        viewModelScope.launch(exceptionHandler(messageHandler)) {
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
        viewModelScope.launch(exceptionHandler(messageHandler)) {
            val settings = settingsRepository.observeSettings().first()
            updateState {
                copy(
                    rotationDuration = settings.rotationDuration,
                    rotationsCount = settings.rotationsCount,
                    selectedGroupId = settings.selectedGroupId,
                    groups = groupsRepository.observeGroups().first()
                )
            }
        }
    }

    private fun observeGroupsChanges() {
        groupsRepository
            .observeGroups()
            .combine(
                settingsRepository.observeSettings()
                    .map { it.selectedGroupId }
                    .distinctUntilChanged()
            ) { groups, selectedGroupId ->
                updateState { copy(groups = groups, selectedGroupId = selectedGroupId) }
            }
            .launchIn(viewModelScope + exceptionHandler(messageHandler))
    }

    private inline fun updateState(updateAction: SettingsViewState.() -> SettingsViewState) {
        _state.value = updateAction.invoke(state.value)
    }
}
