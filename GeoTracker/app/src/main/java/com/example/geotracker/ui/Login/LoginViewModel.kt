package com.example.geotracker.ui.Login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geotracker.LoginState
import com.example.geotracker.data.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val repository = AuthRepository()

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    fun onUsernameChange(nuevoUsername: String) {
        _state.value = _state.value.copy(username = nuevoUsername)
    }

    fun onPasswordChange(nuevoPassword: String) {
        _state.value = _state.value.copy(password = nuevoPassword)
    }

    fun onLoginClic() {
        if (_state.value.username.isBlank() || _state.value.password.isBlank()) {
            _state.value = _state.value.copy(errorMessage = "Por favor completa todos los campos")
            return
        }

        _state.value = _state.value.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            val resultado = repository.login(_state.value.username.trim(), _state.value.password)

            resultado.onSuccess { response ->
                _state.value = _state.value.copy(
                    isLoading = false,
                    loginExito = response.success,
                    errorMessage = if (!response.success) response.message else null
                )
            }.onFailure { error ->
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = error.message
                )
            }
        }
    }
}
