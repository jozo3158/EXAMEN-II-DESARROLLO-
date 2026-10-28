package com.example.geotracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.geotracker.data.TokenManager
import com.example.geotracker.ui.Login.LoginScreen
import com.example.geotracker.ui.captura.CapturaScreen
import com.example.geotracker.ui.dashboard.DashboardScreen
import com.example.geotracker.ui.listado.ListadoScreen
import com.example.geotracker.ui.mapa.MapaScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val inicio = if (TokenManager.obtenerToken() != null) "dashboard" else "login"

    NavHost(navController = navController, startDestination = inicio) {
        composable("login") {
            LoginScreen(
                onLoginExitoso = {
                    navController.navigate("dashboard") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        composable("dashboard") {
            DashboardScreen(
                onCapturar = { navController.navigate("captura") },
                onListado = { navController.navigate("listado") },
                onMapa = { navController.navigate("mapa") },
                onCerrarSesion = {
                    TokenManager.cerrarSesion()
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable("captura") {
            CapturaScreen(onVolver = { navController.popBackStack() })
        }
        composable("listado") {
            ListadoScreen(onVolver = { navController.popBackStack() })
        }
        composable("mapa") {
            MapaScreen(onVolver = { navController.popBackStack() })
        }
    }
}
