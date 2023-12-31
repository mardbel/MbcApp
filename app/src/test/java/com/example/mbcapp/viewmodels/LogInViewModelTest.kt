package com.example.mbcapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mbcapp.model.Token
import com.example.mbcapp.model.TokenData
import com.example.mbcapp.model.TokenResponse
import com.example.mbcapp.repositories.UserAuthRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class LogInViewModelTest {

    private val userAuthRepository: UserAuthRepository = mock()
    private lateinit var logInViewModel: LogInViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun onBefore() {
        logInViewModel = LogInViewModel(userAuthRepository)
    }

    @Test
    fun `when login with correct credentials then success is set in the state`() = runTest {
        //Given
        val token = Token(accessToken = "ff", tokenType = "gg", secondsUntilExpiration = 1, refreshToken = "ff", createdAt = 1)
        val tokenData = TokenData( "1", "Bearer", token)
        val accessTokenResponse = TokenResponse(data = tokenData, null)
        val mail = "aValidMail"
        val password = "aValidPassword"
        whenever(userAuthRepository.logIn(mail, password)).thenReturn(UserAuthRepository.AuthenticationResult.Success(accessTokenResponse))

        //When
        logInViewModel.logInUser(mail, password)

        //Then
        advanceUntilIdle()
        assert(logInViewModel.loginState.value == LogInState.Success)
    }

    @Test
    fun `when login with incorrect password then IncorrectPasswordError is set in the state`() = runTest {
        //Given
        val mail = "aValidMail"
        val password = "aInvalidPassword"
        whenever(userAuthRepository.logIn(mail, password)).thenReturn(UserAuthRepository.AuthenticationResult.IncorrectPassword)

        //When
        logInViewModel.logInUser(mail, password)

        //Then
        advanceUntilIdle()
        assert(logInViewModel.loginState.value == LogInState.IncorrectPasswordError)
    }


}