package com.stellar.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stellar.data.Repository
import com.stellar.data.types.Card
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject



sealed interface AddingNewCardState{

    object Success : AddingNewCardState
    object Loading : AddingNewCardState
    data class Errors(val errors : CardErrors) : AddingNewCardState
    object Idle : AddingNewCardState
}


sealed interface CardError{
    data class Number (val message : String) : CardError
    data class CVV (val message : String): CardError
    data class Name (val message : String): CardError
    data class Date (val message : String): CardError
    data class Other(val e : Exception) : CardError
}


data class CardErrors(
    var numberError: String? = null,
    var cvvError: String? = null,
    var nameError: String? = null,
    var dateError: String? = null,
    var otherError: String? = null
){
    fun hasErrors() : Boolean{
        return  numberError != null || cvvError != null || nameError != null || dateError != null || otherError != null
    }
}






@HiltViewModel
class AddNewCardViewModel @Inject constructor(private val repository: Repository): ViewModel() {


    var newCardState  : AddingNewCardState by mutableStateOf(AddingNewCardState.Idle)

    fun addNewCard(name : String, number: String, cvv : String, date : String){
        newCardState = AddingNewCardState.Loading
        try {
           val errors =  validateCard(name,number, cvv, date)
            if(errors.hasErrors()){
                newCardState = AddingNewCardState.Errors(errors)
                return
            }
        }
        catch(e : Exception){
            newCardState = AddingNewCardState.Errors(CardErrors(otherError = e.localizedMessage))
            return
        }
        viewModelScope.launch {
            try{
                repository.addNewCard(name, number, cvv.toInt(), date)
                newCardState = AddingNewCardState.Success
            }catch (e : Exception){
                newCardState = AddingNewCardState.Errors(CardErrors(otherError = e.localizedMessage))
            }

        }
    }


    private fun validateCard(name : String, number: String, cvv : String, date : String) : CardErrors{

        var cardsErrors = CardErrors()

        // cvv
        try{
            cvv.toInt()
            if(cvv.isEmpty()){
                cardsErrors.cvvError = "Cvv should not be empty"
            }
            else if(cvv.length > 3){
                cardsErrors.cvvError = "Cvv must be no more then 3 numbers"
            }
        }catch(e : NumberFormatException){
            cardsErrors.cvvError = "Cvv must only contain numbers"
        }


        // Card Number
        if(number.isEmpty()){
            cardsErrors.numberError = "Number should not be empty"
        }
        else if(!number.matches(Regex("\\d+"))){
            cardsErrors.numberError = "Card number must be only digits"
        }
        else if(number.length != 16){
            cardsErrors.numberError = "Card number must be 16 digits long"
        }



        // name
        if(name.isEmpty()){
            cardsErrors.nameError = "Name should not be empty"
        }
        else if(!name.matches(Regex("[a-zA-Z]+"))){
            cardsErrors.nameError = "Name must only contain letters"
        }


        // date
        if(date.isEmpty()){
            cardsErrors.dateError = "Date should not be empty"
        }



        return cardsErrors
    }

}