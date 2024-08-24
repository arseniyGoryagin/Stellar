package com.stellar.viewmodels


/*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stellar.Exceptions.NoTokenException
import com.stellar.data.Repository
import com.stellar.data.types.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


    sealed interface UserState{
        data class Success(val userData : User) : UserState
        object Idle : UserState
        data class Error(val e : Exception) : UserState
        object Loading : UserState
    }



    @HiltViewModel
    class UserViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

        var userState : UserState by mutableStateOf(UserState.Loading)


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
                            catch (e : retrofit2.HttpException){
                                val repsonseBody = e.response()?.errorBody()?.string()
                                println("Error ${e.message()}\n${repsonseBody}")
                                repository.clearToken()
                            }
                            catch (e : Exception){

                            }
                        }
                    }


                    userState = UserState.Error(e)
                }
                catch (e : NoTokenException){
                    userState = UserState.Idle
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

*/