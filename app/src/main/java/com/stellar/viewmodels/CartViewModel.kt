package com.stellar.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

    var cartProducts :  CartProductsState by mutableStateOf(CartProductsState.Loading)


    suspend fun fetchCartProducts(){
        cartProducts = CartProductsState.Loading
        val products = repository.getCartProducts()
        cartProducts = CartProductsState.Success(products)
    }


    fun updateCartProducts(){
        viewModelScope.launch {
            try {
               fetchCartProducts()
            }
            catch (e : retrofit2.HttpException){
                repository.clearCart()
                try {
                    fetchCartProducts()
                }catch (e : Exception){
                    cartProducts = CartProductsState.Error(e)
                }
            }
            catch (e:Exception) {
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