package com.stellar.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stellar.data.types.Product
import com.stellar.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject


sealed interface ProductState{
    data class Success(val product: Product) : ProductState
    object Loading : ProductState
    object Error : ProductState
}

@HiltViewModel
class ProductViewModel@Inject constructor(private val repository: Repository) : ViewModel() {

    var productState : ProductState by mutableStateOf(ProductState.Loading)
        private set

    suspend fun getProduct(id : Int){
        try{
            println("GET PRODUCT = \n" + id)

            val product =repository.getProduct(id=id)
            productState = ProductState.Success(product)

        }
        catch(e: Exception){
            productState = ProductState.Error
        }
    }

    fun addProductToCart(productId: Int) {
        viewModelScope.launch {
            repository.addToCart(productId)
        }
    }


}