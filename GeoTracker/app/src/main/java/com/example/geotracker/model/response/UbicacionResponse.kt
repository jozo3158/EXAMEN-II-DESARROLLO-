package com.example.geotracker.model.response

data class UbicacionResponse(
    val id: Int,
    val nombre: String,
    val descripcion: String?,
    val latitud: Double,
    val longitud: Double,
    val fecha_creacion: String?
)
