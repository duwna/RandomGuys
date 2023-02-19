package com.example.randomguys.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomguys.data.MessageEvent
import com.example.randomguys.data.MessageHandler
import com.example.randomguys.data.ResourceManager
import com.example.randomguys.data.exceptionHandler
import com.example.randomguys.data.repositories.GroupsRepository
import com.example.randomguys.data.repositories.SettingsRepository
import com.example.randomguys.domain.models.RouletteGroup
import com.example.randomguys.domain.models.RouletteItem
import com.example.randomguys.navigation.Navigator
import com.example.randomguys.presentation.Screen
import com.example.randomguys.presentation.screens.main.MainViewState.AutoRouletteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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

    private val autoRouletteSelectedItems = mutableListOf<RouletteItem>()

    init {
        observeSettingsChanges()

        _state
            .onEach { if (it.rouletteItems.isEmpty()) createNewGroup() }
            .launchIn(viewModelScope)
    }

    fun onAngleChanged(rotationAngle: Int?) {
        val newState = state.value.copy(
            indicatorState = state.value.indicatorState.copy(
                currentRotationAngle = rotationAngle,
                isAnimating = false
            )
        )

        updateNewRouletteStateAfterRotation(newState)
    }

    fun onRouletteClicked() {
        _state.update {
            it.copy(indicatorState = it.indicatorState.copy(isAnimating = true))
        }
    }

    private fun updateNewRouletteStateAfterRotation(newState: MainViewState) {
        val selectedItem = newState.getSelectedRouletteItem() ?: return
        messageHandler.showMessage(MessageEvent.Message(selectedItem.name, durationMillis = 2000))

        when {
            newState.isAutoRouletteFinished -> showAutoRouletteResult(newState, selectedItem)
            newState.autoRouletteState == AutoRouletteState.Playing -> playNextAutoRouletteIteration(newState, selectedItem)
            else -> _state.update { newState }
        }
    }

    fun navigateToSettings() {
        navigator.navigate(Screen.SETTINGS)
    }

    fun changeAutoRouletteState() {
        when (state.value.autoRouletteState) {
            AutoRouletteState.Off -> {
                _state.update {
                    it.copy(
                        autoRouletteState = AutoRouletteState.Playing,
                        indicatorState = it.indicatorState.copy(isAnimating = true)
                    )
                }
            }
            AutoRouletteState.Playing -> {
                refreshGroup()
            }
            is AutoRouletteState.Success -> {
                refreshGroup()
            }
        }
// TODO
//        if (state.value.rouletteItems.size == 2) {
//            refreshGroup()
//        } else {
//            _state.update {
//                it.copy(rouletteItems = it.rouletteItems.toMutableList().apply { removeAt(0) })
//            }
//        }
    }

    private fun refreshGroup() {
        viewModelScope.launch(exceptionHandler(messageHandler)) {
            val selectedId = settingsRepository.observeSettings().first().selectedGroupId
            val group = groupsRepository.getGroup(selectedId)

            autoRouletteSelectedItems.clear()
            _state.update { it.copy(rouletteItems = group.items, autoRouletteState = AutoRouletteState.Off) }
        }
    }

    private fun playNextAutoRouletteIteration(newState: MainViewState, selectedItem: RouletteItem) {
        viewModelScope.launch(exceptionHandler(messageHandler)) {
            autoRouletteSelectedItems.add(selectedItem)

            delay(1000)
            _state.update {
                newState.copy(rouletteItems = it.rouletteItems.toMutableList().apply { remove(selectedItem) })
            }

            delay(1000)
            _state.update {
                it.copy(indicatorState = it.indicatorState.copy(isAnimating = true))
            }
        }
    }

    private fun showAutoRouletteResult(newState: MainViewState, selectedItem: RouletteItem) {
        viewModelScope.launch(exceptionHandler(messageHandler)) {
            val lastItem = newState.rouletteItems.find { it !in autoRouletteSelectedItems && it != selectedItem }
                ?: error("last item can't be null")

            val autoRouletteState = MainViewState.AutoRouletteState.Success(
                rouletteItems = autoRouletteSelectedItems + selectedItem + lastItem
            )

            autoRouletteSelectedItems.clear()

            delay(1000)
            _state.update { newState.copy(autoRouletteState = autoRouletteState) }
        }
    }

    private fun observeSettingsChanges() {
        settingsRepository
            .observeSettings()
            .combine(groupsRepository.observeGroups()) { settings, groups ->
                _state.update {
                    it.copy(
                        rouletteItems = groups.find { group -> group.id == settings.selectedGroupId }?.items.orEmpty(),
                        indicatorState = it.indicatorState.copy(
                            rotationDuration = settings.rotationDuration,
                            rotationsCount = settings.rotationsCount
                        )
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun createNewGroup() {
        viewModelScope.launch(exceptionHandler(messageHandler)) {
            val itemsSize = 5
            val group = RouletteGroup.create(resourceManager, itemsSize)

            groupsRepository.saveGroup(group)
            settingsRepository.saveSelectedGroupId(group.id)
        }
    }
}
