package com.example.geotracker.ui.captura

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.geotracker.data.LocationProvider
import com.example.geotracker.data.UbicacionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CapturaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UbicacionRepository()
    private val locationProvider = LocationProvider(application)

    private val _state = MutableStateFlow(CapturaState())
    val state: StateFlow<CapturaState> = _state

    fun onNombreChange(nuevo: String) {
        _state.value = _state.value.copy(nombre = nuevo, mensaje = null)
    }

    fun onDescripcionChange(nueva: String) {
        _state.value = _state.value.copy(descripcion = nueva, mensaje = null)
    }

    fun onPermisoDenegado() {
        _state.value = _state.value.copy(
            mensaje = "Se necesita el permiso de ubicación para capturar",
            esError = true
        )
    }

    fun onCapturarClic() {
        if (_state.value.nombre.isBlank()) {
            _state.value = _state.value.copy(mensaje = "Escribe un nombre para la ubicación", esError = true)
            return
        }

        _state.value = _state.value.copy(isLoading = true, mensaje = null)

        viewModelScope.launch {
            val ubicacion = try {
                locationProvider.obtenerUbicacionActual()
            } catch (e: Exception) {
                null
            }

            if (ubicacion == null) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    mensaje = "No se pudo obtener la ubicación. Activa el GPS e inténtalo de nuevo",
                    esError = true
                )
                return@launch
            }

            val resultado = repository.guardarUbicacion(
                _state.value.nombre.trim(),
                _state.value.descripcion.trim(),
                ubicacion.latitude,
                ubicacion.longitude
            )

            resultado.onSuccess { response ->
                _state.value = if (response.success) {
                    CapturaState(mensaje = "Ubicación guardada correctamente", esError = false)
                } else {
                    _state.value.copy(isLoading = false, mensaje = response.message, esError = true)
                }
            }.onFailure { error ->
                _state.value = _state.value.copy(
                    isLoading = false,
                    mensaje = error.message,
                    esError = true
                )
            }
        }
    }
}
