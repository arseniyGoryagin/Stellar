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
    object Error : UserState
    object Loading : UserState
}


sealed interface AuthState{
    object NotAuthenticated  : AuthState
    object Authenticated : AuthState
}



@HiltViewModel
class UserViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    var userState : UserState by mutableStateOf(UserState.Idle)
    var authState : AuthState by mutableStateOf(AuthState.NotAuthenticated)

    fun login(email: String, password : String){
        viewModelScope.launch {
            try {
                userState = UserState.Loading
                val userData = repository.login(email, password)
                userState = UserState.Success(userData)
            }
            catch (e : Exception){
                when(e){
                    is retrofit2.HttpException ->{
                        val repsonseBody = e.response()?.errorBody()?.string()
                        println("Error ${e.message()}\n${repsonseBody}")
                        userState = UserState.Idle
                    }
                    else -> {
                        userState = UserState.Error
                    }
                }
            }
        }
    }



    fun register(email: String, password : String, name : String){
        viewModelScope.launch {
            try {
                userState = UserState.Loading
                val userData = repository.registerUser(email = email, password = password, name = name)
                userState = UserState.Success(userData)
                println("User registered!")
            }
            catch (e : Exception){
                when(e){
                    is retrofit2.HttpException ->{
                        val repsonseBody = e.response()?.errorBody()?.string()
                        println("Error ${e.message()}\n${repsonseBody}")
                        userState = UserState.Idle

                    }
                    else -> {
                        userState = UserState.Error
                    }
                }
            }
        }
    }

    init {
        viewModelScope.launch {

            checkAuthState()


            println("Auth state === " + authState)

            if (authState == AuthState.NotAuthenticated){
                return@launch
            }


            try {
                refreshToken()
            }catch (e : Exception){
                when(e){
                    is retrofit2.HttpException -> {
                        repository.clearToken()
                        authState = AuthState.NotAuthenticated
                    }
                }
            }


            updateUserData()
        }
    }

    private suspend fun refreshToken(){
            repository.refreshToken()
    }

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
    }


    private suspend fun fetchUserData(){
        val userData = repository.getUserData()
        userState = UserState.Success(userData)
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
                            fetchUserData()
                        }
                        catch (e : Exception){
                            when(e){
                                is retrofit2.HttpException -> {
                                    val repsonseBody = e.response()?.errorBody()?.string()
                                    println("Error ${e.message()}\n${repsonseBody}")
                                    repository.clearToken()
                                    authState = AuthState.NotAuthenticated
                                    userState = UserState.Idle
                                }
                            }
                            userState = UserState.Error
                        }
                    }
                }
            }
        }
        userState = UserState.Error
    }


    fun resetState(){
        userState = UserState.Idle
    }


    fun logout(){
        userState = UserState.Idle
        authState = AuthState.NotAuthenticated
    }


    fun updateUserData(){
        viewModelScope.launch {
            try {
                userState = UserState.Loading
                fetchUserData()
            }
            catch (e : Exception){
               handleException(e)
            }
        }
    }


}