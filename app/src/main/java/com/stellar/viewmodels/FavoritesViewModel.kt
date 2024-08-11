package com.stellar.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stellar.data.types.Product
import com.stellar.data.Repository
import com.stellar.data.types.FavoriteProductWithProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject

sealed interface FavoriteProductsState {
    data class Success(val products : List<FavoriteProductWithProduct>) : FavoriteProductsState
    object  Error: FavoriteProductsState
    object Loading : FavoriteProductsState

}


@HiltViewModel
class FavoritesViewModel @Inject constructor(private val repository: Repository) : ViewModel() {


    var favoriteProductsState : FavoriteProductsState by mutableStateOf(FavoriteProductsState.Loading)
        private set

    suspend fun getFavoriteProducts(){
        try {
            val products = repository.getFavoriteProducts()
            favoriteProductsState = FavoriteProductsState.Success(products)
        }
        catch (e : Exception){
            favoriteProductsState = FavoriteProductsState.Error
        }
    }

    suspend fun updateFavoriteProducts(){
        viewModelScope.launch {
            try {
                favoriteProductsState = FavoriteProductsState.Loading
                val products = repository.getFavoriteProducts()
                favoriteProductsState = FavoriteProductsState.Success(products)
            } catch (e: Exception) {
                when(e){
                    is retrofit2.HttpException ->{
                        val repsonseBody = e.response()?.errorBody()?.string()
                        println("Error ${e.message()}\n${repsonseBody}")
                    }
                }
                favoriteProductsState = FavoriteProductsState.Error
            }
        }
    }


    fun addFavorite(id : Int){
        viewModelScope.launch {
            val updatedProducts: List<FavoriteProductWithProduct> =
                (favoriteProductsState as FavoriteProductsState.Success).products.map {
                    if (id == it.product.id) {
                        it.favorite = true
                    }
                    it
                }

            favoriteProductsState = FavoriteProductsState.Success(updatedProducts)

            repository.addFavorite(id)
        }
    }

    fun removeFavorite(id : Int){
        viewModelScope.launch {
            val updatedProducts: List<FavoriteProductWithProduct> =
                (favoriteProductsState as FavoriteProductsState.Success).products.map {
                    if (id == it.product.id) {
                        it.favorite = true
                    }
                    it
                }
            favoriteProductsState = FavoriteProductsState.Success(updatedProducts)
            repository.removeFavorite(id)
        }
    }


    init {
        viewModelScope.launch {
            getFavoriteProducts()
        }
    }




}