package com.example.geotracker.data

import android.content.Context
import android.content.SharedPreferences

object TokenManager {
    private var prefs: SharedPreferences? = null

    fun init(context: Context) {
        prefs = context.applicationContext.getSharedPreferences("geotracker_prefs", Context.MODE_PRIVATE)
    }

    fun guardarSesion(token: String, username: String) {
        prefs?.edit()?.putString("token", token)?.putString("username", username)?.apply()
    }

    fun obtenerToken(): String? = prefs?.getString("token", null)

    fun obtenerUsername(): String = prefs?.getString("username", "") ?: ""

    fun cerrarSesion() {
        prefs?.edit()?.clear()?.apply()
    }
}
