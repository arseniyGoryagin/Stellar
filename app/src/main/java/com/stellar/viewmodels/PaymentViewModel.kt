package com.stellar.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stellar.data.Repository
import com.stellar.data.types.Card
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject




sealed interface CardState{
    data class Success(val cards : List<Card>) : CardState
    object Loading : CardState
    data class Error(val error : Exception) : CardState
}

sealed interface MakingOrderState{
    object Success : MakingOrderState
    object Loading : MakingOrderState
    object Idle : MakingOrderState
    data class Error(val error : Exception) : MakingOrderState
}

@HiltViewModel
class PaymentViewModel @Inject constructor (private val repository: Repository) : ViewModel() {

    var cartProducts :  CartProductsState by mutableStateOf(CartProductsState.Loading)
    var cardsState : CardState = CardState.Loading

    var makingOrderState : MakingOrderState by mutableStateOf(MakingOrderState.Idle)

    fun updateCartProducts(){
        viewModelScope.launch {
            try {
                cartProducts = CartProductsState.Loading
                val products = repository.getCartProducts()
                cartProducts = CartProductsState.Success(products)

            } catch (e: IOException) {
                cartProducts = CartProductsState.Error(e)
            }
        }
    }

    fun updateCards(){
        viewModelScope.launch {
            try {
                cardsState = CardState.Loading
                val cards = repository.getAllCards()
                cardsState = CardState.Success(cards)

            } catch (e: IOException) {
                cardsState = CardState.Error(e)
            }
        }
    }

    fun makeOrder(){
        viewModelScope.launch {
            try {
                makingOrderState = MakingOrderState.Loading
                val cartProductsState = cartProducts
                when(cartProductsState){
                    is CartProductsState.Error -> TODO()
                    CartProductsState.Loading -> TODO()
                    is CartProductsState.Success -> {

                        for (product in cartProductsState.products){
                            val totalPrice = product.product.price * product.cartProduct.qty
                            repository.addOrder(product.product.id, product.cartProduct.qty, totalPrice)
                        }
                        repository.clearCart()
                    }
                }

                makingOrderState = MakingOrderState.Success

            }catch (e : Exception){
                makingOrderState = MakingOrderState.Error(e)
            }
        }
    }


    fun restOrderState(){
        makingOrderState = MakingOrderState.Idle
    }



    init {
        updateCartProducts()
    }


}


