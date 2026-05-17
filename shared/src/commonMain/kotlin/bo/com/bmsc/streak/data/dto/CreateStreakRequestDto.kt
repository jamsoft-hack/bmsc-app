package bo.com.bmsc.streak.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateStreakRequestDto(
    @SerialName("participant_ids") val participantIds: List<String>,
)
