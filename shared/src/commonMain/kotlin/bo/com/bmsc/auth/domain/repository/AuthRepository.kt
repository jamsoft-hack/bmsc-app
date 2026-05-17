package bo.com.bmsc.auth.domain.repository

import bo.com.bmsc.auth.domain.model.AuthResult
import bo.com.bmsc.auth.domain.model.LoginRequest
import bo.com.bmsc.core.domain.DataError
import bo.com.bmsc.core.domain.Result

interface AuthRepository {
    suspend fun login(request: LoginRequest): Result<AuthResult, DataError.Remote>
}
