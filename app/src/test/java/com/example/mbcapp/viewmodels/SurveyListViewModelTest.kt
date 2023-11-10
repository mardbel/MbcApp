package com.example.mbcapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.mbcapp.model.*
import com.example.mbcapp.repositories.SurveyRepository
import com.example.mbcapp.repositories.UserAuthRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class SurveyListViewModelTest {

    private val surveyRepository: SurveyRepository = mock()
    private val userAuthRepository: UserAuthRepository = mock()
    private lateinit var surveyListViewModel: SurveyListViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun onBefore() {
        surveyListViewModel = SurveyListViewModel(surveyRepository, userAuthRepository)
    }

    @Test
    fun `when fetch survey list with valid token then success is set in the state`() = runTest {
        //Given
        val survey = Survey("a title", "a description", "imageURL")
        val surveyData = listOf(SurveyData("12", "Bearer", survey))
        val surveyResponse = SurveyRepository.SurveyFetchResult.Success(surveyData)
        whenever(surveyRepository.fetchSurveys()).thenReturn(flowOf(surveyResponse))

        //When
        val observer: Observer<List<SurveyData>> = mock()
        surveyListViewModel.surveyList.observeForever(observer)

        //Then
        advanceUntilIdle()
        assert(surveyListViewModel.surveyList.value == surveyData)
    }
}