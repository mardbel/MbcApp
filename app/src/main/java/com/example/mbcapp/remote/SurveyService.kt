package com.example.mbcapp.remote

import com.example.mbcapp.model.SurveyResponse
import com.example.mbcapp.remote.SurveyService.Companion.Resource.API_SURVEY_ENDPOINT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SurveyService {

    companion object {
        object Resource {
            const val API_SURVEY_ENDPOINT = "api/v1/surveys"
        }
    }

    @GET(API_SURVEY_ENDPOINT)
    suspend fun fetchSurveys(
        @Header("Authorization") authorization: String?,
        @Query("page[number]") page: Int,
        @Query("page[size]") size: Int
    ): Response<SurveyResponse>
}