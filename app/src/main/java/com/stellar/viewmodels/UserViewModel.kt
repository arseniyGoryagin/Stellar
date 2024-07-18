package com.stellar.viewmodels

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stellar.data.Repository
import com.stellar.data.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject



sealed interface UserState{
    data class Success(val userData : User) : UserState
    object Error : UserState
    object Loading : UserState
    object NonAuthenticated : UserState
}


@HiltViewModel
class UserViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    var userState : UserState by mutableStateOf(UserState.NonAuthenticated)

    fun login(email: String, password : String){
        viewModelScope.launch {
            try {
                userState = UserState.Loading
                val userData = repository.login(email, password)
                userState = UserState.Success(userData)
            }
            catch (e : Exception){
                userState = UserState.Error
            }
        }
    }



    fun register(email: String, password : String, name : String){
        viewModelScope.launch {
            try {
                userState = UserState.Loading
                val userData = repository.registerUser(email, password, name, "")
                userState = UserState.Success(userData)
                println("User registered!")
            }
            catch (e : Exception){
                userState = UserState.Error
                println("Errorrr!!!!!" + e.localizedMessage)
            }
        }
    }




    fun updateUserData(){
        viewModelScope.launch {
            try {
                userState = UserState.Loading
                val userData = repository.getUserData()
                userState = UserState.Success(userData)
            }
            catch (e : Exception){
                userState = UserState.Error
            }
        }
    }


}