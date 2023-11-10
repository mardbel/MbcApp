package com.example.mbcapp.repositories

import com.example.mbcapp.model.SurveyData
import com.example.mbcapp.model.SurveyResponse
import kotlinx.coroutines.flow.Flow

interface SurveyRepository {

    suspend fun fetchSurveys() : Flow<SurveyFetchResult<List<SurveyData>?>>


    sealed class SurveyFetchResult<out T> {
        class Success<out T>(val surveyList: T) : SurveyFetchResult<T>()
        object IncorrectPassword : SurveyFetchResult<Nothing?>()
        object InvalidClient : SurveyFetchResult<Nothing?>()
        object NetworkError : SurveyFetchResult<Nothing?>()
        object BadRequest : SurveyFetchResult<Nothing?>()
        object ApiError : SurveyFetchResult<Nothing?>()
        class GenericError<out T>(val throwable: Throwable) : SurveyFetchResult<T>()
        object NoToken : SurveyFetchResult<Nothing?>()
    }
}