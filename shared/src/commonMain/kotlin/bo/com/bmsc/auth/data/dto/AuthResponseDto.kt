package bo.com.bmsc.auth.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponseDto(
    @SerialName("id") val id: String,
    @SerialName("username") val username: String,
    @SerialName("email") val email: String = "",
    @SerialName("token") val token: String,
)
