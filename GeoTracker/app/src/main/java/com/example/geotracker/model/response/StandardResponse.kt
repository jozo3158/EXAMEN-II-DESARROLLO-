package com.example.geotracker.model.response

data class StandardResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T?
)
