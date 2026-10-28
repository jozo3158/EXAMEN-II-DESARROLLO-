package com.example.geotracker.ui.listado

import com.example.geotracker.model.response.UbicacionResponse

data class ListadoState(
    val isLoading: Boolean = false,
    val ubicaciones: List<UbicacionResponse> = emptyList(),
    val errorMessage: String? = null
)
