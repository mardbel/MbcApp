package com.example.mbcapp.viewmodels

import androidx.lifecycle.*
import com.example.mbcapp.model.SurveyData
import com.example.mbcapp.repositories.SurveyRepository
import com.example.mbcapp.repositories.UserAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SurveyListViewModel @Inject constructor(
    private val surveyRepository: SurveyRepository,
    private val userAuthRepository: UserAuthRepository
) : ViewModel() {

    private val mSurveyList = flow {
        surveyRepository.fetchSurveys().collect {
            when (it) {
                is SurveyRepository.SurveyFetchResult.Success -> emit(it.surveyList!!)
                else -> mError.value = "error generico" //TODO
            }
        }
    }.asLiveData()

    val surveyList: LiveData<List<SurveyData>>
        get() = mSurveyList

    private val mError = MutableLiveData<String>()

    val error: LiveData<String>
        get() = mError

}

