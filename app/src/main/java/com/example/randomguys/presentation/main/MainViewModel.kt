package com.example.randomguys.presentation.main

import androidx.lifecycle.ViewModel
import com.example.randomguys.data.SettingsRepository
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

    fun onAngleChanged(rotationAngle: Int?) {
        _state.value = state.value.copy(rouletteRotationAngle = rotationAngle)
    }
}
