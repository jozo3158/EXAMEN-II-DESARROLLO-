package com.example.geotracker.data

import com.example.geotracker.data.remote.RetrofitClient
import com.example.geotracker.model.request.UbicacionRequest
import com.example.geotracker.model.response.StandardResponse
import com.example.geotracker.model.response.UbicacionResponse
import com.google.gson.Gson

class UbicacionRepository {

    private val apiService = RetrofitClient.apiService
    private val gson = Gson()

    private fun extraerMensajeError(errorBody: String?): String {
        return try {
            gson.fromJson(errorBody, StandardResponse::class.java).message
        } catch (e: Exception) {
            "Ocurrió un error en el servidor"
        }
    }

    suspend fun guardarUbicacion(
        nombre: String,
        descripcion: String,
        latitud: Double,
        longitud: Double
    ): Result<StandardResponse<UbicacionResponse>> {
        return try {
            val response = apiService.guardarUbicacion(
                UbicacionRequest(nombre, descripcion, latitud, longitud)
            )
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Result.success(body)
            } else {
                Result.failure(Exception(extraerMensajeError(response.errorBody()?.string())))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de conexión: ${e.message}"))
        }
    }

    suspend fun listarUbicaciones(): Result<List<UbicacionResponse>> {
        return try {
            val response = apiService.listarUbicaciones()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Result.success(body.data ?: emptyList())
            } else {
                Result.failure(Exception(extraerMensajeError(response.errorBody()?.string())))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de conexión: ${e.message}"))
        }
    }
}
