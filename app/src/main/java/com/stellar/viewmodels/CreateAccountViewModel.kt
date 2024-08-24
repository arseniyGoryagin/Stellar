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
                repository.registerUser(email = email, password = password, name = name)
                registerState= RegisterState.Success
            }
            catch (e : retrofit2.HttpException){
                registerState = RegisterState.Error(e)
            }
            catch (e : Exception){
                registerState = RegisterState.Error(e)
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