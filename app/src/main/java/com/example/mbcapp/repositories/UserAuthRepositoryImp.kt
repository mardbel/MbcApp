package com.example.mbcapp.repositories

import com.example.mbcapp.model.AccessTokenResponse
import com.example.mbcapp.model.LoginUser
import com.example.mbcapp.remote.AuthenticationService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UserAuthRepositoryImp @Inject constructor(
    private val authenticationService: AuthenticationService,
) : UserAuthRepository {

    override suspend fun logIn(
        email: String,
        password: String
    ): UserAuthRepository.AuthenticationResult {
        return try {
            val response = authenticationService.logInUser(
                loginUserBody = LoginUser(email = email, password = password)
            )

            if (response.isSuccessful) {
                return UserAuthRepository.AuthenticationResult.Success(response.body()!!)

            } else UserAuthRepository.AuthenticationResult.NoToken
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> UserAuthRepository.AuthenticationResult.NetworkError
                is HttpException -> when (throwable.code()) {
                    400 -> UserAuthRepository.AuthenticationResult.IncorrectPassword
                    in 401..499 -> UserAuthRepository.AuthenticationResult.BadRequest
                    in 500..599 -> UserAuthRepository.AuthenticationResult.ApiError
                    else -> UserAuthRepository.AuthenticationResult.GenericError(throwable)
                }
                else -> UserAuthRepository.AuthenticationResult.GenericError(throwable)
            }
        }
    }

    override suspend fun saveAccessToken(token: AccessTokenResponse) {
        TODO("Not yet implemented")
    }


}