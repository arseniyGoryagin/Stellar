package com.stellar.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stellar.data.Repository
import com.stellar.data.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.stellar.R



sealed interface RegisterState{
    object Success : RegisterState
    object Idle : RegisterState
    data class Error(val e : Exception): RegisterState
    object Loading : RegisterState
}



@HiltViewModel
class CreateAccountViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    var registerState : RegisterState by mutableStateOf(RegisterState.Idle)

    fun register(email: String, password : String, name : String){
        viewModelScope.launch {
            try {
                registerState = RegisterState.Loading
                repository.registerUserAndAuth(email = email, password = password, name = name)
               // repository.saveToken()
                registerState= RegisterState.Success
            }
            catch (e : Exception){
                when(e){
                    is retrofit2.HttpException ->{
                        //val repsonseBody = e.response()?.errorBody()?.string()
                        registerState = RegisterState.Error(e)
                    }
                    else -> {
                        registerState = RegisterState.Error(e)
                    }
                }
            }
        }
    }

   fun sendNotification(name : String, description : String, icon : Int ){
       viewModelScope.launch {
           repository.addNotification(name = name, description = description, icon = icon)
       }
    }
    fun resetState(){
        registerState = RegisterState.Idle
    }

}