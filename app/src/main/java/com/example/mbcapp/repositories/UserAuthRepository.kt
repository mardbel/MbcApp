package com.example.mbcapp.repositories

import com.example.mbcapp.model.AccessTokenResponse
import com.example.mbcapp.remote.AuthenticationService

interface UserAuthRepository {

    suspend fun logIn(email: String, password: String): AuthenticationResult

    sealed class AuthenticationResult {
        class Success(val token: AccessTokenResponse) : AuthenticationResult()
        object IncorrectPassword : AuthenticationResult()
        object InvalidClient : AuthenticationResult()
        object NetworkError : AuthenticationResult()
        object BadRequest : AuthenticationResult()
        object ApiError : AuthenticationResult()
        class GenericError(val throwable: Throwable) : AuthenticationResult()
        object NoToken : AuthenticationResult()
    }
}