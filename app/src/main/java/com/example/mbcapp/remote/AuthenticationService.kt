package com.example.mbcapp.remote

import com.example.mbcapp.model.AccessTokenResponse
import com.example.mbcapp.remote.AuthenticationService.Companion.Resource.API_TOKEN_ENDPOINT
import com.example.mbcapp.remote.AuthenticationService.Companion.Resource.PASSWORD
import retrofit2.Response
import retrofit2.http.*

interface AuthenticationService {

    companion object {
        object Resource {
            const val API_TOKEN_ENDPOINT = "v1/oauth/token"
            const val PASSWORD = "password"
        }
    }
    @Headers("Content-Type: application/json; charset=utf-8")
    @FormUrlEncoded
    @POST(API_TOKEN_ENDPOINT)
    suspend fun getNewAccessToken(
        @Field("grant_type") grantType: String = PASSWORD,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
    ): Response<AccessTokenResponse>
}