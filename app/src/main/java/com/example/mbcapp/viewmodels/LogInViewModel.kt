package com.example.mbcapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mbcapp.model.AccessTokenResponse
import com.example.mbcapp.repositories.UserAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val userAuthRepository: UserAuthRepository
) : ViewModel() {

    private val mState = MutableLiveData<LogInState>()
    val state: LiveData<LogInState>
        get() = mState


    fun logInUser(email: String, password: String) {
        viewModelScope.launch {
            val result = userAuthRepository.logIn(email, password)
            when (result) {
                    is UserAuthRepository.AuthenticationResult.Success -> saveAccessToken(result.token)
                    is UserAuthRepository.AuthenticationResult.IncorrectPassword -> mState.value = LogInState.IncorrectPasswordError
                    is UserAuthRepository.AuthenticationResult.InvalidClient, -> mState.value = LogInState.InvalidClientError
                    is UserAuthRepository.AuthenticationResult.GenericError,
                        UserAuthRepository.AuthenticationResult.NetworkError,
                        UserAuthRepository.AuthenticationResult.BadRequest,
                        UserAuthRepository.AuthenticationResult.ApiError,
                        UserAuthRepository.AuthenticationResult.NoToken -> mState.value = LogInState.GenericError("unknown error")
            }
        }
    }

    private fun saveAccessToken(token: AccessTokenResponse) {
        viewModelScope.launch {
            userAuthRepository.saveAccessToken(token)
        }
    }

}

sealed class LogInState {
    class GenericError(val cause: String) : LogInState()
    object IncorrectPasswordError : LogInState()
    object InvalidClientError : LogInState()
    object Success : LogInState()
}

