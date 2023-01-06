package com.example.randomguys.presentation.screens.group_edition

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.randomguys.data.repositories.SettingsRepository
import com.example.randomguys.domain.models.RouletteGroup
import com.example.randomguys.domain.models.RouletteItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val repository: SettingsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow<GroupViewState>(
        GroupViewState(
            RouletteGroup(1, emptyList())
        )
    )
    val state = _state.asStateFlow()

    init {
        println(
            GroupEditionNavArgs(savedStateHandle)
        )
    }

    private fun loadGroup() {

    }

    fun onTextChanged(itemIndex: Int, text: String) {
        updateItemInState(itemIndex) { copy(name = text) }
    }

    private fun updateItemInState(index: Int, transform: RouletteItem.() -> RouletteItem) {
        _state.update {
            it.copy(group = it.group.copy(items = it.group.items.toMutableList().apply {
                this[index] = transform.invoke(this[index])
            }))
        }
    }
}
