package com.stellar.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stellar.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface LoginState{
    object Success : LoginState
    object Idle : LoginState
    data class Error(val e : Exception): LoginState
    object Loading : LoginState
}



@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    var loginState : LoginState by mutableStateOf(LoginState.Idle)


    fun login(email: String, password : String){
        viewModelScope.launch {
            try {
                loginState = LoginState.Loading
                repository.login(email, password)
                repository.saveToken()
                loginState = LoginState.Success
            }
            catch (e : Exception){
                when(e){
                    is retrofit2.HttpException ->{
                        loginState = LoginState.Error(e)
                    }
                    else -> {
                        loginState = LoginState.Error(e)
                    }
                }
            }
        }
    }

    fun resetState(){
        loginState = LoginState.Idle
    }

}