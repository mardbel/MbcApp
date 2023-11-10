package com.example.mbcapp.remote

import com.example.mbcapp.model.LoginUser
import com.example.mbcapp.model.TokenResponse
import com.example.mbcapp.remote.AuthenticationService.Companion.Resource.API_TOKEN_ENDPOINT
import retrofit2.Response
import retrofit2.http.*

interface AuthenticationService {

    companion object {
        object Resource {
            const val API_TOKEN_ENDPOINT = "api/v1/oauth/token"
        }
    }

    @POST(API_TOKEN_ENDPOINT)
    suspend fun logInUser(
        @Body loginUserBody: LoginUser
    ): Response<TokenResponse>
}