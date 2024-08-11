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
import kotlinx.serialization.descriptors.StructureKind
import okhttp3.Authenticator
import javax.inject.Inject
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import coil.network.HttpException


sealed interface UserState{
    data class Success(val userData : User) : UserState
    object Idle : UserState
    data class Error(val e : Exception) : UserState
    object Loading : UserState
}


/*
sealed interface AuthState{
    object NotAuthenticated  : AuthState
    object Authenticated : AuthState
}*/



@HiltViewModel
class UserViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    var userState : UserState by mutableStateOf(UserState.Idle)



    suspend fun fetchUserData(){
        userState = UserState.Loading
        val userData = repository.getUserData()
        userState = UserState.Success(userData)
    }

    fun updateUserData(){


        viewModelScope.launch {
            try {
                fetchUserData()
            }
            catch (e : retrofit2.HttpException){
                when(e.code()){
                    401 ->{
                        try {
                            repository.refreshToken()
                            fetchUserData()
                            return@launch
                        }
                        catch (e : Exception){
                            when(e){
                                is retrofit2.HttpException -> {
                                    val repsonseBody = e.response()?.errorBody()?.string()
                                    println("Error ${e.message()}\n${repsonseBody}")
                                    repository.clearToken()
                                }
                            }
                            println("Exccpetion in refresh")
                        }
                    }
                }
                userState = UserState.Error(e)
            }
            catch (e : Exception){
                println("Exccpetion in ser")
                userState = UserState.Error(e)
            }
        }
    }

    fun resetState(){
        userState = UserState.Idle
    }


    init {
        updateUserData()
    }
}