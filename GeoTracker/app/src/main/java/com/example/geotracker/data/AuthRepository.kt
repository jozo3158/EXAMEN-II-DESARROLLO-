package com.example.geotracker.data

import com.example.geotracker.data.remote.RetrofitClient
import com.example.geotracker.model.request.LoginRequest
import com.example.geotracker.model.response.LoginResponse
import com.example.geotracker.model.response.StandardResponse
import com.google.gson.Gson

class AuthRepository {

    private val apiService = RetrofitClient.apiService
    private val gson = Gson()

    private fun extraerMensajeError(errorBody: String?): String {
        return try {
            gson.fromJson(errorBody, StandardResponse::class.java).message
        } catch (e: Exception) {
            "Credenciales incorrectas"
        }
    }

    suspend fun login(usuario: String, password: String): Result<StandardResponse<LoginResponse>> {
        return try {
            val response = apiService.login(LoginRequest(usuario, password))
            val body = response.body()
            if (response.isSuccessful && body != null) {
                body.data?.let { TokenManager.guardarSesion(it.token, it.username) }
                Result.success(body)
            } else {
                val mensaje = extraerMensajeError(response.errorBody()?.string())
                Result.failure(Exception(mensaje))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de conexión: ${e.message}"))
        }
    }
}
