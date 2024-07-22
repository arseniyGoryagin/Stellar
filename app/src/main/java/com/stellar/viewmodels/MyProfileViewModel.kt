package com.stellar.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.referentialEqualityPolicy
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stellar.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject



sealed interface SaveDataState{
    object Success : SaveDataState
    object Loading : SaveDataState
    object Error : SaveDataState
    object Idle : SaveDataState
}



@HiltViewModel
class MyProfileViewModel @Inject constructor(private val repository: Repository) : ViewModel(){


    var saveDataState : SaveDataState by mutableStateOf(SaveDataState.Idle)


    fun resetState(){
        saveDataState = SaveDataState.Idle
    }

    fun updateUserData(name: String, email: String) {
        viewModelScope.launch {
            try {
                saveDataState = SaveDataState.Loading
                repository.updateUserData(name = name, email = email)
                saveDataState = SaveDataState.Success
            } catch (e: Exception) {
                when (e) {
                    is retrofit2.HttpException -> {
                        val repsonseBody = e.response()?.errorBody()?.string()
                        println("Error ${e.message()}\n${repsonseBody}")
                    }
                }
                println("Errro " + e.localizedMessage)
                saveDataState = SaveDataState.Error

            }
        }
    }


}