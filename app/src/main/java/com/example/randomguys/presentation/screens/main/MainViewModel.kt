package com.example.randomguys.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomguys.data.MessageEvent
import com.example.randomguys.data.MessageHandler
import com.example.randomguys.data.ResourceManager
import com.example.randomguys.data.launchHandlingErrors
import com.example.randomguys.data.repositories.GroupsRepository
import com.example.randomguys.data.repositories.SettingsRepository
import com.example.randomguys.domain.models.RouletteGroup
import com.example.randomguys.domain.models.RouletteItem
import com.example.randomguys.navigation.Navigator
import com.example.randomguys.presentation.Screen
import com.example.randomguys.presentation.screens.main.MainViewState.AutoRouletteState
import com.example.randomguys.presentation.utils.mutableEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val groupsRepository: GroupsRepository,
    private val navigator: Navigator,
    private val messageHandler: MessageHandler,
    private val resourceManager: ResourceManager
) : ViewModel() {

    private val _state = MutableStateFlow(MainViewState())
    val state = _state.asStateFlow()

    private val _event = mutableEventFlow<MainScreenEvent>()
    val event = _event.asSharedFlow()

    private val autoRouletteSelectedItems = mutableListOf<RouletteItem>()

    init {
        observeSettingsChanges()
    }

    fun onAngleChanged(rotationAngle: Int?) {
        val newState = state.value.copy(rouletteRotationAngle = rotationAngle)
        updateNewRouletteStateAfterRotation(newState)
    }

    private fun updateNewRouletteStateAfterRotation(newState: MainViewState) {
        val selectedItem = newState.getSelectedRouletteItem() ?: return
        messageHandler.showMessage(MessageEvent.Message(selectedItem.name, durationMillis = 2000))

        when {
            newState.isAutoRouletteFinished -> showAutoRouletteResult(newState, selectedItem)
            newState.autoRouletteState == AutoRouletteState.Playing -> playNextAutoRouletteIteration(newState, selectedItem)
            else -> updateState { newState }
        }
    }

    fun navigateToSettings() {
        navigator.navigate(Screen.SETTINGS)
    }

    fun changeAutoRouletteState() {
        when (state.value.autoRouletteState) {
            AutoRouletteState.Off -> {
                updateState { copy(autoRouletteState = AutoRouletteState.Playing) }
                _event.tryEmit(MainScreenEvent.StartRoulette)
            }
            AutoRouletteState.Playing -> {
                refreshGroup()
                _event.tryEmit(MainScreenEvent.StopRoulette)
            }
            is AutoRouletteState.Success -> {
                refreshGroup()
            }
        }
    }

    private fun refreshGroup() {
        viewModelScope.launchHandlingErrors(messageHandler) {
            val selectedId = settingsRepository.observeSettings().first().selectedGroupId
            val group = groupsRepository.getGroup(selectedId)

            autoRouletteSelectedItems.clear()
            updateState { copy(rouletteItems = group.items, autoRouletteState = AutoRouletteState.Off) }
        }
    }

    private fun playNextAutoRouletteIteration(newState: MainViewState, selectedItem: RouletteItem) {
        viewModelScope.launchHandlingErrors(messageHandler) {
            autoRouletteSelectedItems.add(selectedItem)

            delay(1000)
            updateState {
                newState.copy(rouletteItems = rouletteItems.toMutableList().apply { remove(selectedItem) })
            }

            delay(1000)
            _event.tryEmit(MainScreenEvent.StartRoulette)
        }
    }

    private fun showAutoRouletteResult(newState: MainViewState, selectedItem: RouletteItem) {
        viewModelScope.launchHandlingErrors(messageHandler) {
            val lastItem = newState.rouletteItems.find { it !in autoRouletteSelectedItems && it != selectedItem }
                ?: error("last item can't be null")

            val autoRouletteState = AutoRouletteState.Success(
                rouletteItems = autoRouletteSelectedItems + selectedItem + lastItem
            )

            autoRouletteSelectedItems.clear()

            delay(1000)
            updateState { newState.copy(autoRouletteState = autoRouletteState) }
        }
    }

    private fun observeSettingsChanges() {
        settingsRepository
            .observeSettings()
            .combine(groupsRepository.observeGroups()) { settings, groups ->

                if (groups.isEmpty()) createNewGroup()

                updateState {
                    copy(
                        rouletteItems = groups.find { it.id == settings.selectedGroupId }?.items.orEmpty(),
                        rotationDuration = settings.rotationDuration,
                        rotationsCount = settings.rotationsCount
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun createNewGroup() {
        viewModelScope.launchHandlingErrors(messageHandler) {
            val itemsSize = 5
            val group = RouletteGroup.create(resourceManager, itemsSize)

            groupsRepository.saveGroup(group)
            settingsRepository.saveSelectedGroupId(group.id)
        }
    }

    private inline fun updateState(updateAction: MainViewState.() -> MainViewState) {
        _state.value = updateAction.invoke(state.value)
    }
}
