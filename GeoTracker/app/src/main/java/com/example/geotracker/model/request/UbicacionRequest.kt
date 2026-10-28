package com.example.geotracker.model.request

data class UbicacionRequest(
    val nombre: String,
    val descripcion: String,
    val latitud: Double,
    val longitud: Double
)
