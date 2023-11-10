package com.example.mbcapp.repositories

import com.example.mbcapp.model.Token
import com.example.mbcapp.model.TokenData
import com.example.mbcapp.model.TokenResponse

interface UserAuthRepository {

    suspend fun logIn(email: String, password: String): AuthenticationResult

    suspend fun saveAccessToken(token: TokenResponse)

    suspend fun refreshToken(refreshToken: String) : AuthenticationResult

    suspend fun getAccessToken() : Token

    sealed class AuthenticationResult {
        class Success(val token: TokenResponse) : AuthenticationResult()
        object IncorrectPassword : AuthenticationResult()
        object InvalidClient : AuthenticationResult()
        object NetworkError : AuthenticationResult()
        object BadRequest : AuthenticationResult()
        object ApiError : AuthenticationResult()
        class GenericError(val throwable: Throwable) : AuthenticationResult()
        object NoToken : AuthenticationResult()
    }
}