package bo.com.bmsc.streak.domain.repository

import bo.com.bmsc.core.domain.DataError
import bo.com.bmsc.core.domain.Result
import bo.com.bmsc.streak.domain.model.Contact
import bo.com.bmsc.streak.domain.model.CreateStreakRequest
import bo.com.bmsc.streak.domain.model.CreateStreakResult

interface CreateStreakRepository {
    suspend fun getContacts(): Result<List<Contact>, DataError>
    suspend fun createStreak(request: CreateStreakRequest): Result<CreateStreakResult, DataError.Remote>
}
