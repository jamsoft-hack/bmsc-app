package bo.com.bmsc.streak.data.repository

import bo.com.bmsc.core.domain.DataError
import bo.com.bmsc.core.domain.Result
import bo.com.bmsc.core.domain.map
import bo.com.bmsc.streak.data.datasource.CreateStreakRemoteDataSource
import bo.com.bmsc.streak.data.dto.CreateStreakRequestDto
import bo.com.bmsc.streak.domain.model.Contact
import bo.com.bmsc.streak.domain.model.CreateStreakRequest
import bo.com.bmsc.streak.domain.model.CreateStreakResult
import bo.com.bmsc.streak.domain.repository.CreateStreakRepository

class CreateStreakRepositoryImpl(
    private val remoteDataSource: CreateStreakRemoteDataSource,
) : CreateStreakRepository {

    override suspend fun getContacts(): Result<List<Contact>, DataError> =
        remoteDataSource.getContacts().map { dtos ->
            dtos.map { dto ->
                Contact(
                    id = dto.id,
                    name = dto.name,
                    initials = dto.initials,
                )
            }
        }

    override suspend fun createStreak(
        request: CreateStreakRequest,
    ): Result<CreateStreakResult, DataError.Remote> =
        remoteDataSource.createStreak(
            CreateStreakRequestDto(participantIds = request.participantIds)
        ).map { dto ->
            CreateStreakResult(streakId = dto.streakId)
        }
}
