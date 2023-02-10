package com.example.randomguys.presentation.screens.group_edition

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomguys.R
import com.example.randomguys.data.MessageEvent
import com.example.randomguys.data.MessageHandler
import com.example.randomguys.data.ResourceManager
import com.example.randomguys.data.launchHandlingErrors
import com.example.randomguys.data.messageHandler
import com.example.randomguys.data.repositories.GroupsRepository
import com.example.randomguys.domain.models.RouletteGroup
import com.example.randomguys.domain.models.RouletteItem
import com.example.randomguys.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val repository: GroupsRepository,
    private val errorHandler: MessageHandler,
    private val navigator: Navigator,
    private val messageHandler: MessageHandler,
    private val resourceManager: ResourceManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val args = GroupEditionNavArgs(savedStateHandle)

    private val _state = MutableStateFlow(GroupViewState())
    val state = _state.asStateFlow()

    init {
        if (args.groupId != null) loadGroup(args.groupId) else createGroup()
        startSavingGroupOnDataChanges()
    }

    fun onTextChanged(itemIndex: Int, text: String) = updateMembersList {
        this[itemIndex] = this[itemIndex].copy(name = text)
    }

    fun removeMember(index: Int) {
        updateMembersList {
            removeAt(index)
        }

        if (checkNotNull(state.value.group).items.isEmpty()) {
            errorHandler.showMessage(MessageEvent.Id(R.string.no_members_in_group_message))
            viewModelScope.launchHandlingErrors(messageHandler) {
                delay(500)
                navigator.popBackStack()
            }
        }
    }

    fun addMember() = updateMembersList {
        add(RouletteItem.create(resourceManager))
    }

    fun showColorPicker(index: Int) {
        _state.update { it.copy(isColorPickerVisibleForIndex = index) }
    }

    fun onColorUpdated(color: Color?) {
        val index = state.value.isColorPickerVisibleForIndex

        _state.update { it.copy(isColorPickerVisibleForIndex = null) }

        if (index != null && color != null) {
            updateMembersList { this[index] = this[index].copy(color = color) }
        }
    }

    private fun updateMembersList(updateAction: MutableList<RouletteItem>.() -> Unit) = _state.update {
        val group = it.group ?: return
        it.copy(group = group.copy(items = group.items.toMutableList().apply(updateAction)))
    }

    private fun loadGroup(groupId: String) {
        viewModelScope.launchHandlingErrors(errorHandler) {
            _state.update { it.copy(group = repository.getGroup(groupId)) }
        }
    }

    private fun createGroup() {
        val group = RouletteGroup.create(resourceManager)
        _state.update { it.copy(group = group) }
    }

    private fun startSavingGroupOnDataChanges() {
        _state
            .onStart { delay(500) }
            .distinctUntilChanged { old, new -> old.group?.items == new.group?.items }
            .debounce(500)
            .mapNotNull { it.group }
            .onEach(repository::saveGroup)
            .launchIn(viewModelScope + messageHandler(errorHandler))
    }
}
