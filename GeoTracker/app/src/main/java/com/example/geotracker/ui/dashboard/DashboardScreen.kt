package com.example.geotracker.ui.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.geotracker.data.TokenManager

@Composable
fun DashboardScreen(
    onCapturar: () -> Unit,
    onListado: () -> Unit,
    onMapa: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    val username = TokenManager.obtenerUsername()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Bienvenido, $username", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.padding(16.dp))

        Button(onClick = onCapturar, modifier = Modifier.fillMaxWidth()) {
            Text("Capturar ubicación")
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Button(onClick = onListado, modifier = Modifier.fillMaxWidth()) {
            Text("Mis ubicaciones")
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Button(onClick = onMapa, modifier = Modifier.fillMaxWidth()) {
            Text("Ver mapa")
        }
        Spacer(modifier = Modifier.padding(16.dp))
        OutlinedButton(onClick = onCerrarSesion, modifier = Modifier.fillMaxWidth()) {
            Text("Cerrar sesión")
        }
    }
}
