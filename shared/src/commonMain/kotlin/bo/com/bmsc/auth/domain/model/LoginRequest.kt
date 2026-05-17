package bo.com.bmsc.auth.domain.model

data class LoginRequest(
    val username: String,
    val password: String,
    val rememberMe: Boolean,
)
