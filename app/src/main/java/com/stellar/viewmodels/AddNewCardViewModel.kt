package com.stellar.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stellar.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject



sealed interface AddingNewCardState{

    object Success : AddingNewCardState
    object Loading : AddingNewCardState
    data class Error(val e : Exception) : AddingNewCardState
    object Idle : AddingNewCardState
}


@HiltViewModel
class AddNewCardViewModel @Inject constructor(private val repository: Repository): ViewModel() {


        var newCardState  : AddingNewCardState by mutableStateOf(AddingNewCardState.Idle)



    fun addNewCard(name : String, number: String, cvv : Int, date : String){
        viewModelScope.launch {
            try{
                newCardState = AddingNewCardState.Loading
                repository.addNewCard(name, number, cvv, date)
                newCardState = AddingNewCardState.Success
            }catch (e : Exception){
                newCardState = AddingNewCardState.Error(e)
            }

        }
    }

    fun resetState(){
        newCardState = AddingNewCardState.Idle
    }


}