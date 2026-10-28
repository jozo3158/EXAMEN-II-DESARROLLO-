package com.example.geotracker.ui.mapa

import com.example.geotracker.model.response.UbicacionResponse

data class MapaState(
    val isLoading: Boolean = false,
    val ubicaciones: List<UbicacionResponse> = emptyList(),
    val errorMessage: String? = null,
    val seleccionada: UbicacionResponse? = null,
    val mostrarDialogo: Boolean = false
)
