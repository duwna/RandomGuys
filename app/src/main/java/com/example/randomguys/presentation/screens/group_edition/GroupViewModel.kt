package com.example.randomguys.presentation.screens.group_edition

import androidx.lifecycle.ViewModel
import com.example.randomguys.data.repositories.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val repository: SettingsRepository
) : ViewModel() {

    private val _state = MutableStateFlow<GroupViewState?>(null)
    val state = _state.asStateFlow().filterNotNull()

    init {
        loadGroup()
    }

    private fun loadGroup() {

    }
}
