package com.example.mbcapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
            when (userAuthRepository.logIn(email, password)) {
                    is UserAuthRepository.AuthenticationResult.Success -> mState.value = LogInState.Success
                    is UserAuthRepository.AuthenticationResult.IncorrectPassword -> mState.value = LogInState.GenericError("wrong credentials")
                    is UserAuthRepository.AuthenticationResult.GenericError,
                        UserAuthRepository.AuthenticationResult.InvalidClient,
                        UserAuthRepository.AuthenticationResult.NetworkError,
                        UserAuthRepository.AuthenticationResult.BadRequest,
                        UserAuthRepository.AuthenticationResult.ApiError,
                        UserAuthRepository.AuthenticationResult.NoToken -> mState.value = LogInState.GenericError("unknown error")
            }
        }
    }

}

sealed class LogInState {
    class GenericError(val cause: String) : LogInState()
    object Success : LogInState()
}

