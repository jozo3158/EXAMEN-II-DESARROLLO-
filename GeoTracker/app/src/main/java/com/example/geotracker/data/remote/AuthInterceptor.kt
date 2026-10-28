package com.example.geotracker.data.remote

import com.example.geotracker.data.TokenManager
import okhttp3.Interceptor
import okhttp3.Response

// Agrega automaticamente el token en el header Authorization de cada peticion
class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        TokenManager.obtenerToken()?.let { token ->
            builder.addHeader("Authorization", "Bearer $token")
        }
        return chain.proceed(builder.build())
    }
}
