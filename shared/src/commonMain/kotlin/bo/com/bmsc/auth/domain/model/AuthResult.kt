package bo.com.bmsc.auth.domain.model

data class AuthResult(
    val user: User,
    val accessToken: String,
)
