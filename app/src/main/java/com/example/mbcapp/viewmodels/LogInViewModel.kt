package com.example.mbcapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mbcapp.model.TokenData
import com.example.mbcapp.model.TokenResponse
import com.example.mbcapp.repositories.UserAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val userAuthRepository: UserAuthRepository
) : ViewModel() {

    private val mLogInState = MutableLiveData<LogInState>()
    val loginState: LiveData<LogInState>
        get() = mLogInState


    fun logInUser(email: String, password: String) {
        viewModelScope.launch {
            when (val result = userAuthRepository.logIn(email, password)) {
                    is UserAuthRepository.AuthenticationResult.Success -> saveAccessToken(result.token)
                    is UserAuthRepository.AuthenticationResult.IncorrectPassword -> mLogInState.value = LogInState.IncorrectPasswordError
                    is UserAuthRepository.AuthenticationResult.InvalidClient, -> mLogInState.value = LogInState.InvalidClientError
                    is UserAuthRepository.AuthenticationResult.GenericError,
                        UserAuthRepository.AuthenticationResult.NetworkError,
                        UserAuthRepository.AuthenticationResult.BadRequest,
                        UserAuthRepository.AuthenticationResult.ApiError,
                        UserAuthRepository.AuthenticationResult.NoToken -> mLogInState.value = LogInState.GenericError("unknown error")
            }
        }
    }

    private fun saveAccessToken(token: TokenResponse) {
        viewModelScope.launch {
            mLogInState.value = LogInState.Success
        }
    }

}

sealed class LogInState {
    class GenericError(val cause: String) : LogInState()
    object IncorrectPasswordError : LogInState()
    object InvalidClientError : LogInState()
    object Success : LogInState()
}

