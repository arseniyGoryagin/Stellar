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

    fun updateUserData(){
        viewModelScope.launch {
            try {
                userState = UserState.Loading
                val userData = repository.getUserData()
                userState = UserState.Success(userData)
            }
            catch (e : Exception){
                handleException(e)
            }
        }
    }

    fun resetState(){
        userState = UserState.Idle
    }

    suspend fun refreshoken(){
       repository.refreshToken()
    }

    private suspend fun handleException(e : Exception){
        when(e){
            is retrofit2.HttpException ->{
                val repsonseBody = e.response()?.errorBody()?.string()
                println("Error ${e.message()}\n${repsonseBody}")


                when(e.code()){
                    401 ->{
                        try {
                            repository.refreshToken()
                            updateUserData()
                        }
                        catch (e : Exception){
                            when(e){
                                is retrofit2.HttpException -> {
                                    val repsonseBody = e.response()?.errorBody()?.string()
                                    println("Error ${e.message()}\n${repsonseBody}")
                                    repository.clearToken()
                                    userState = UserState.Error(e)
                                }
                            }
                            userState = UserState.Error(e)
                        }
                    }
                    else -> userState = UserState.Error(e)
                }


            }
        }
        userState = UserState.Error(e)
    }

    init {
        updateUserData()
    }



    /*
    private fun checkAuthState(){
        val jwtToken = repository.getJwt()

        println("VIEW MODEL JWTTTTT == " + jwtToken.access_token)
        println("VIEW MODEL REFRESH == " + jwtToken.refresh_token)

        if(jwtToken.refresh_token == null || jwtToken.access_token == null){
            println("Some one is nulll")
            authState = AuthState.NotAuthenticated
        }else {
            println("Authenticatd !!!!!!")
            authState = AuthState.Authenticated
        }
    }*/







}