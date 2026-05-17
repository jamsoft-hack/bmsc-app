package bo.com.bmsc.streak.data.datasource

import bo.com.bmsc.core.domain.DataError
import bo.com.bmsc.core.domain.Result
import bo.com.bmsc.streak.data.dto.ContactDto
import bo.com.bmsc.streak.data.dto.CreateStreakRequestDto
import bo.com.bmsc.streak.data.dto.CreateStreakResponseDto

interface CreateStreakRemoteDataSource {
    suspend fun getContacts(): Result<List<ContactDto>, DataError>
    suspend fun createStreak(request: CreateStreakRequestDto): Result<CreateStreakResponseDto, DataError.Remote>
}
