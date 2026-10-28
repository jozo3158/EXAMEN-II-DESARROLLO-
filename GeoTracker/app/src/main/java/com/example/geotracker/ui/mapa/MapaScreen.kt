package com.example.geotracker.ui.mapa

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.geotracker.ui.util.BarraSuperior
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun MapaScreen(
    viewModel: MapaViewModel = viewModel(),
    onVolver: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val camara = rememberCameraPositionState()
    var mapaCargado by remember { mutableStateOf(false) }

    // Centra la camara para que todos los marcadores sean visibles
    LaunchedEffect(state.ubicaciones, mapaCargado) {
        if (mapaCargado && state.ubicaciones.isNotEmpty()) {
            if (state.ubicaciones.size == 1) {
                val unica = state.ubicaciones.first()
                camara.animate(
                    CameraUpdateFactory.newLatLngZoom(LatLng(unica.latitud, unica.longitud), 15f)
                )
            } else {
                val limites = LatLngBounds.Builder()
                state.ubicaciones.forEach { limites.include(LatLng(it.latitud, it.longitud)) }
                camara.animate(CameraUpdateFactory.newLatLngBounds(limites.build(), 120))
            }
        }
    }

    Scaffold(topBar = { BarraSuperior("Mapa de ubicaciones", onVolver) }) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = camara,
                onMapLoaded = { mapaCargado = true }
            ) {
                state.ubicaciones.forEach { ubicacion ->
                    val estadoMarcador = rememberMarkerState(
                        key = ubicacion.id.toString(),
                        position = LatLng(ubicacion.latitud, ubicacion.longitud)
                    )
                    Marker(
                        state = estadoMarcador,
                        onClick = {
                            viewModel.onMarcadorClic(ubicacion)
                            true // true evita que se muestre el InfoWindow predeterminado
                        }
                    )
                }
            }

            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            state.errorMessage?.let { mensaje ->
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = mensaje, color = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.padding(8.dp))
                    Button(onClick = { viewModel.cargar() }) { Text("Reintentar") }
                }
            }

            if (!state.isLoading && state.errorMessage == null && state.ubicaciones.isEmpty()) {
                Text(
                    text = "No tienes ubicaciones guardadas todavía",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(24.dp)
                )
            }
        }
    }

    val seleccionada = state.seleccionada
    if (state.mostrarDialogo && seleccionada != null) {
        AlertDialog(
            onDismissRequest = { viewModel.cerrarDialogo() },
            title = { Text(seleccionada.nombre) },
            text = {
                Column {
                    Text(
                        text = if (seleccionada.descripcion.isNullOrBlank()) "Sin descripción" else seleccionada.descripcion,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.padding(6.dp))
                    Text(text = "Latitud: ${seleccionada.latitud}", style = MaterialTheme.typography.bodySmall)
                    Text(text = "Longitud: ${seleccionada.longitud}", style = MaterialTheme.typography.bodySmall)
                }
            },
            confirmButton = {
                TextButton(onClick = { viewModel.cerrarDialogo() }) { Text("Cerrar") }
            }
        )
    }
}
