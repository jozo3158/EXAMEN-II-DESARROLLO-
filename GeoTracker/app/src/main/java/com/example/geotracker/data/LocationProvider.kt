package com.example.geotracker.data

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeoutOrNull

// Obtiene la ubicacion GPS actual con FusedLocationProviderClient
class LocationProvider(private val context: Context) {

    @SuppressLint("MissingPermission")
    suspend fun obtenerUbicacionActual(): Location? {
        val client = LocationServices.getFusedLocationProviderClient(context)
        val cts = CancellationTokenSource()
        val actual = withTimeoutOrNull(15_000) {
            client.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cts.token).await()
        }
        if (actual == null) cts.cancel()
        return actual ?: client.lastLocation.await()
    }
}
