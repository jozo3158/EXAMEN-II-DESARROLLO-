package com.example.geotracker.ui.listado

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.geotracker.model.response.UbicacionResponse
import com.example.geotracker.ui.util.BarraSuperior

@Composable
fun ListadoScreen(
    viewModel: ListadoViewModel = viewModel(),
    onVolver: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    Scaffold(topBar = { BarraSuperior("Mis ubicaciones", onVolver) }) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                state.isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

                state.errorMessage != null -> Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = state.errorMessage ?: "", color = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.padding(8.dp))
                    Button(onClick = { viewModel.cargar() }) { Text("Reintentar") }
                }

                state.ubicaciones.isEmpty() -> Text(
                    text = "Aún no has guardado ubicaciones",
                    modifier = Modifier.align(Alignment.Center)
                )

                else -> LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.ubicaciones, key = { it.id }) { ubicacion ->
                        TarjetaUbicacion(ubicacion)
                    }
                }
            }
        }
    }
}

@Composable
private fun TarjetaUbicacion(ubicacion: UbicacionResponse) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = ubicacion.nombre,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            if (!ubicacion.descripcion.isNullOrBlank()) {
                Spacer(modifier = Modifier.padding(2.dp))
                Text(text = ubicacion.descripcion, style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = "Latitud: ${ubicacion.latitud}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Longitud: ${ubicacion.longitud}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
