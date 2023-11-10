package com.example.mbcapp.repositories

import com.example.mbcapp.model.SurveyData
import com.example.mbcapp.remote.AuthenticationService
import com.example.mbcapp.remote.SurveyService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import retrofit2.HttpException
import java.io.IOException

class SurveyRepositoryImp(
    private val surveyService: SurveyService,
    private val userRepository: UserAuthRepository,
    ) : SurveyRepository {

    override suspend fun fetchSurveys(): Flow<SurveyRepository.SurveyFetchResult<List<SurveyData>?>> {

        return try {
        val response = surveyService.fetchSurveys(authorization = "Bearer ${userRepository.getAccessToken().accessToken}", 1, 10)
        if (response.isSuccessful) {
            flowOf(SurveyRepository.SurveyFetchResult.Success(response.body()!!.data))
        } else flowOf(SurveyRepository.SurveyFetchResult.NetworkError)
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> flowOf(SurveyRepository.SurveyFetchResult.NetworkError)
                is HttpException -> when (throwable.code()) {
                    400 -> flowOf(SurveyRepository.SurveyFetchResult.BadRequest)
                    401 -> {
                        userRepository.refreshToken(userRepository.getAccessToken().refreshToken)
                        fetchSurveys()
                    }
                    in 402..499 -> flowOf(SurveyRepository.SurveyFetchResult.BadRequest)
                    in 500..599 -> flowOf(SurveyRepository.SurveyFetchResult.ApiError)
                    else -> flowOf(SurveyRepository.SurveyFetchResult.GenericError(throwable))
                    }
                    else -> flowOf(SurveyRepository.SurveyFetchResult.GenericError(throwable))
            }
        }
    }

}