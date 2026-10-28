package com.example.geotracker.data.remote

import com.example.geotracker.model.request.LoginRequest
import com.example.geotracker.model.request.UbicacionRequest
import com.example.geotracker.model.response.LoginResponse
import com.example.geotracker.model.response.StandardResponse
import com.example.geotracker.model.response.UbicacionResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("api/login")
    suspend fun login(@Body request: LoginRequest): Response<StandardResponse<LoginResponse>>

    @POST("api/locations")
    suspend fun guardarUbicacion(@Body request: UbicacionRequest): Response<StandardResponse<UbicacionResponse>>

    @GET("api/locations")
    suspend fun listarUbicaciones(): Response<StandardResponse<List<UbicacionResponse>>>
}
