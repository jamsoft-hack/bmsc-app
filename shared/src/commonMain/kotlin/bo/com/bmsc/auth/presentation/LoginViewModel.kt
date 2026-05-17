package bo.com.bmsc.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bo.com.bmsc.auth.domain.model.LoginRequest
import bo.com.bmsc.auth.domain.repository.AuthRepository
import bo.com.bmsc.core.domain.DataError
import bo.com.bmsc.core.domain.Result
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val rememberMe: Boolean = true,
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
) {
    val isLoginEnabled: Boolean
        get() = username.isNotBlank() && password.isNotBlank() && !isLoading
}

sealed interface LoginEvent {
    data object NavigateToHome : LoginEvent
    data object NavigateToRegister : LoginEvent
    data object NavigateToForgotPassword : LoginEvent
    data object NavigateToFaceId : LoginEvent
}

class LoginViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _events = Channel<LoginEvent>()
    val events = _events.receiveAsFlow()

    fun onUsernameChange(value: String) {
        _uiState.update { it.copy(username = value, error = null) }
    }

    fun onPasswordChange(value: String) {
        _uiState.update { it.copy(password = value, error = null) }
    }

    fun onRememberMeChange(value: Boolean) {
        _uiState.update { it.copy(rememberMe = value) }
    }

    fun onPasswordVisibilityToggle() {
        _uiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun onLoginClick() {
        val state = _uiState.value
        if (!state.isLoginEnabled) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            val result = authRepository.login(
                LoginRequest(
                    username = state.username,
                    password = state.password,
                    rememberMe = state.rememberMe,
                )
            )

            when (result) {
                is Result.Success -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _events.send(LoginEvent.NavigateToHome)
                }
                is Result.Error -> {
                    val message = when (result.error) {
                        DataError.Remote.NO_INTERNET -> "Sin conexión a internet. Verificá tu red."
                        DataError.Remote.REQUEST_TIMEOUT -> "La solicitud tardó demasiado. Intentá de nuevo."
                        DataError.Remote.SERVER -> "Error del servidor. Intentá más tarde."
                        else -> "Usuario o contraseña incorrectos."
                    }
                    _uiState.update { it.copy(isLoading = false, error = message) }
                }
            }
        }
    }

    fun onCreateAccountClick() {
        viewModelScope.launch { _events.send(LoginEvent.NavigateToRegister) }
    }

    fun onForgotPasswordClick() {
        viewModelScope.launch { _events.send(LoginEvent.NavigateToForgotPassword) }
    }

    fun onFaceIdClick() {
        viewModelScope.launch { _events.send(LoginEvent.NavigateToFaceId) }
    }
}
