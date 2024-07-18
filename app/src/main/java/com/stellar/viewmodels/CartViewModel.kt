package com.stellar.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stellar.data.Product
import com.stellar.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject




sealed interface CartProductsState{
    data class Success(val products : List<Product>) : CartProductsState
    object Error : CartProductsState
    object Loading : CartProductsState
}

@HiltViewModel
class CartViewModel @Inject constructor(private val repository: Repository) : ViewModel(){

    var cartProducts :  CartProductsState = CartProductsState.Loading



    fun updateCartProducts(){
        viewModelScope.launch {
            try {
                cartProducts = CartProductsState.Loading
                val products = repository.getCartProducts()
                cartProducts = CartProductsState.Success(products)

            } catch (e: IOException) {
                cartProducts = CartProductsState.Error
            }
        }
    }


    init {
        updateCartProducts()
    }

}