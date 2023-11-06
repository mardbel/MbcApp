package com.example.mbcapp.repositories

import com.example.mbcapp.remote.AuthenticationService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UserAuthRepositoryImp @Inject constructor(
    private val authenticationService: AuthenticationService,
) : UserAuthRepository {

    private val clientId = "ofzl-2h5ympKa0WqqTzqlVJUiRsxmXQmt5tkgrlWnOE"
    private val clientSecret = "lMQb900L-mTeU-FVTCwyhjsfBwRCxwwbCitPob96cuU"

    override suspend fun logIn(
        email: String,
        password: String
    ): UserAuthRepository.AuthenticationResult {
        return try {
            val response = authenticationService.getNewAccessToken(
                email = email,
                password = password,
                clientId = clientId,
                clientSecret = clientSecret
            )

            if (response.isSuccessful) {
                return UserAuthRepository.AuthenticationResult.Success(response.body()!!)

            } else UserAuthRepository.AuthenticationResult.NoToken
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> UserAuthRepository.AuthenticationResult.NetworkError
                is HttpException -> when (throwable.code()) {
                    in 400..499 -> UserAuthRepository.AuthenticationResult.BadRequest
                    in 500..599 -> UserAuthRepository.AuthenticationResult.ApiError
                    else -> UserAuthRepository.AuthenticationResult.GenericError(throwable)
                }
                else -> UserAuthRepository.AuthenticationResult.GenericError(throwable)
            }
        }
    }


}