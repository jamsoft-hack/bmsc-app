package bo.com.bmsc.auth.data.repository

import bo.com.bmsc.auth.data.datasource.AuthRemoteDataSource
import bo.com.bmsc.auth.data.dto.LoginRequestDto
import bo.com.bmsc.auth.domain.model.AuthResult
import bo.com.bmsc.auth.domain.model.LoginRequest
import bo.com.bmsc.auth.domain.model.User
import bo.com.bmsc.auth.domain.repository.AuthRepository
import bo.com.bmsc.core.domain.DataError
import bo.com.bmsc.core.domain.Result
import bo.com.bmsc.core.domain.map

class AuthRepositoryImpl(
    private val remoteDataSource: AuthRemoteDataSource,
) : AuthRepository {

    override suspend fun login(request: LoginRequest): Result<AuthResult, DataError.Remote> =
        remoteDataSource.login(
            LoginRequestDto(
                username = request.username,
                password = request.password,
            )
        ).map { dto ->
            AuthResult(
                user = User(
                    id = dto.id,
                    username = dto.username,
                    email = dto.email,
                ),
                accessToken = dto.token,
            )
        }
}
