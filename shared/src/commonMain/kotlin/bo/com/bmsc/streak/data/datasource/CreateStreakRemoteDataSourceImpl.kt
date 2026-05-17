package bo.com.bmsc.streak.data.datasource

import bo.com.bmsc.core.data.safeCall
import bo.com.bmsc.core.domain.DataError
import bo.com.bmsc.core.domain.Result
import bo.com.bmsc.streak.data.dto.ContactDto
import bo.com.bmsc.streak.data.dto.CreateStreakRequestDto
import bo.com.bmsc.streak.data.dto.CreateStreakResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class CreateStreakRemoteDataSourceImpl(
    private val client: HttpClient,
) : CreateStreakRemoteDataSource {

    override suspend fun getContacts(): Result<List<ContactDto>, DataError> =
        Result.Success(mockContacts())

    override suspend fun createStreak(
        request: CreateStreakRequestDto,
    ): Result<CreateStreakResponseDto, DataError.Remote> =
        Result.Success(CreateStreakResponseDto(streakId = "streak_${System.currentTimeMillis()}"))

    private fun mockContacts(): List<ContactDto> = listOf(
        ContactDto(id = "1", name = "Ana Vargas", initials = "AV"),
        ContactDto(id = "2", name = "Carlos Rojas", initials = "CR"),
        ContactDto(id = "3", name = "Lucía Mamani", initials = "LM"),
        ContactDto(id = "4", name = "Diego Sotomayor", initials = "DS"),
        ContactDto(id = "5", name = "Sofía López", initials = "SL"),
        ContactDto(id = "6", name = "Mateo Quiroz", initials = "MQ"),
    )
}
