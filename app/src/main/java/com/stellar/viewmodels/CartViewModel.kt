package com.stellar.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stellar.data.Repository
import com.stellar.data.types.CartProductWithProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject




sealed interface CartProductsState{
    data class Success(val products : List<CartProductWithProduct>) : CartProductsState
    data class Error(val error : Exception): CartProductsState
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
                cartProducts = CartProductsState.Error(e)
            }
        }
    }

    fun addItemQty(itemId : Long){
        viewModelScope.launch {
            repository.addItemQty(itemId)
        }
    }

    fun removeItemQty(itemId : Long){
        viewModelScope.launch {
            repository.removeCartProductQty(itemId)
        }
    }
    fun removeFromCart(itemId : Long){
        viewModelScope.launch {
                repository.removeFromCart(itemId)

                var updatedCartProducts :  MutableList<CartProductWithProduct> = mutableListOf()
                for (product in (cartProducts as CartProductsState.Success).products){
                        if(product.cartProduct.id == itemId){
                            continue
                        }
                        updatedCartProducts.add(product)
                }
                cartProducts = CartProductsState.Success(updatedCartProducts)
        }
    }


    init {
        updateCartProducts()
    }

}