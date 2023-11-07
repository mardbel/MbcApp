package com.example.mbcapp.repositories

import com.example.mbcapp.model.AccessTokenResponse
import com.example.mbcapp.model.LoginUser
import com.example.mbcapp.remote.AuthenticationService
import com.example.mbcapp.utils.SharePreferencesProvider
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UserAuthRepositoryImp @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val sharePreferencesProvider: SharePreferencesProvider,
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
                    in 401..402 -> UserAuthRepository.AuthenticationResult.BadRequest
                    403 -> UserAuthRepository.AuthenticationResult.InvalidClient
                    in 500..599 -> UserAuthRepository.AuthenticationResult.ApiError
                    else -> UserAuthRepository.AuthenticationResult.GenericError(throwable)
                }
                else -> UserAuthRepository.AuthenticationResult.GenericError(throwable)
            }
        }
    }

    override suspend fun saveAccessToken(token: AccessTokenResponse) {
        sharePreferencesProvider.saveToken(token)
    }


}