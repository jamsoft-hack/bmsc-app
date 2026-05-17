package bo.com.bmsc.streak.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContactDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("initials") val initials: String,
)
