package com.example.geotracker.ui.captura

data class CapturaState(
    val nombre: String = "",
    val descripcion: String = "",
    val isLoading: Boolean = false,
    val mensaje: String? = null,
    val esError: Boolean = false
)
