package com.example.geotracker

data class LoginState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val loginExito: Boolean = false,
    val errorMessage: String? = null
)
