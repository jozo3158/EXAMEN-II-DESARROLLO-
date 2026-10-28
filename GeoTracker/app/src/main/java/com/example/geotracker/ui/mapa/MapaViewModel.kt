package com.example.geotracker.ui.mapa

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geotracker.data.UbicacionRepository
import com.example.geotracker.model.response.UbicacionResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MapaViewModel : ViewModel() {

    private val repository = UbicacionRepository()

    private val _state = MutableStateFlow(MapaState())
    val state: StateFlow<MapaState> = _state

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

    // La visibilidad del dialogo y el marcador elegido viven en el ViewModel
    fun onMarcadorClic(ubicacion: UbicacionResponse) {
        _state.value = _state.value.copy(seleccionada = ubicacion, mostrarDialogo = true)
    }

    fun cerrarDialogo() {
        _state.value = _state.value.copy(seleccionada = null, mostrarDialogo = false)
    }
}
