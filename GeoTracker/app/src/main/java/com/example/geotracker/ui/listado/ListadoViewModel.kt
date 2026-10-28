package com.example.geotracker.ui.listado

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geotracker.data.UbicacionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListadoViewModel : ViewModel() {

    private val repository = UbicacionRepository()

    private val _state = MutableStateFlow(ListadoState())
    val state: StateFlow<ListadoState> = _state

    init {
        cargar()
    }

    fun cargar() {
        _state.value = _state.value.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            repository.listarUbicaciones()
                .onSuccess { lista ->
                    _state.value = _state.value.copy(isLoading = false, ubicaciones = lista)
                }
                .onFailure { error ->
                    _state.value = _state.value.copy(isLoading = false, errorMessage = error.message)
                }
        }
    }
}
