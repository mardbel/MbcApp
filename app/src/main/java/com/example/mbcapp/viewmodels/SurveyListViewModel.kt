package com.example.mbcapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mbcapp.model.SurveyData
import com.example.mbcapp.repositories.SurveyRepository
import com.example.mbcapp.repositories.UserAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SurveyListViewModel @Inject constructor(
    private val surveyRepository: SurveyRepository,
    private val userAuthRepository: UserAuthRepository
) : ViewModel() {

    init {
        getSurveyList()
    }

    private val mSurveyList = MutableLiveData<List<SurveyData>>()

    val surveyList: LiveData<List<SurveyData>>
        get() = mSurveyList

    private val mError = MutableLiveData<String>()

    val error: LiveData<String>
        get() = mError

    private fun getSurveyList() {
        viewModelScope.launch {
            surveyRepository.fetchSurveys().collect {
                when (it) {
                    is SurveyRepository.SurveyFetchResult.Success -> mSurveyList.value = it.surveyList
                    else -> mError.value = "error generico" //TODO
                }
            }
        }
    }

}

