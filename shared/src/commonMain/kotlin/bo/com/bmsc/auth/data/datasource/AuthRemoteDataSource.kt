package bo.com.bmsc.auth.data.datasource

import bo.com.bmsc.auth.data.dto.AuthResponseDto
import bo.com.bmsc.auth.data.dto.LoginRequestDto
import bo.com.bmsc.core.domain.DataError
import bo.com.bmsc.core.domain.Result

interface AuthRemoteDataSource {
    suspend fun login(request: LoginRequestDto): Result<AuthResponseDto, DataError.Remote>
}
