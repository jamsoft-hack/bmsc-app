package bo.com.bmsc.streak.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateStreakResponseDto(
    @SerialName("streak_id") val streakId: String,
)
