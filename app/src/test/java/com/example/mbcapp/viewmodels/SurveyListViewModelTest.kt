package com.example.mbcapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mbcapp.repositories.SurveyRepository
import com.example.mbcapp.repositories.UserAuthRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class SurveyListViewModelTest {

    private val surveyRepository: SurveyRepository = mock()
    private lateinit var surveyListViewModel: SurveyListViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun onBefore() {
        surveyListViewModel = SurveyListViewModel(surveyRepository)
    }

    @Test
    fun `when fetch survey list with valid token then success is set in the state`() = runTest {

    }

    @Test
    fun `when fetch survey list with invalid token then token is refreshed`() = runTest {

    }

    @Test
    fun `when fetch survey list with invalid page number then error state is set`() = runTest {

    }

}