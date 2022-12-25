package com.example.randomguys.presentation.main

import androidx.lifecycle.ViewModel
import com.example.randomguys.data.SettingsRepository
import com.example.randomguys.models.RouletteItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    repository: SettingsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MainViewState(repository.items))
    val state = _state.asStateFlow()

    fun selectItem(rouletteItem: RouletteItem?) {
        _state.value = state.value.copy(selectedName = rouletteItem?.name)
    }
}
