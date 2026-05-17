package bo.com.bmsc.auth.data.datasource

import bo.com.bmsc.auth.data.dto.AuthResponseDto
import bo.com.bmsc.auth.data.dto.LoginRequestDto
import bo.com.bmsc.core.data.safeCall
import bo.com.bmsc.core.domain.DataError
import bo.com.bmsc.core.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class AuthRemoteDataSourceImpl(
    private val client: HttpClient,
) : AuthRemoteDataSource {

//    override suspend fun login(request: LoginRequestDto): Result<AuthResponseDto, DataError.Remote> =
//        safeCall {
//            client.post("auth/login") {
//                setBody(request)
//            }
//        }
    override suspend fun login(
        request: LoginRequestDto,
    ): Result<AuthResponseDto, DataError.Remote> {

        return if (
            request.username == "aron6625" &&
            request.password == "34353435"
        ) {
            Result.Success(
                AuthResponseDto(
                    id = "1",
                    username = "aron6625",
                    email = "aron@test.com",
                    token = "mock_token_123"
                )
            )
        } else {
            Result.Error(DataError.Remote.SERVER)
        }
    }
}
