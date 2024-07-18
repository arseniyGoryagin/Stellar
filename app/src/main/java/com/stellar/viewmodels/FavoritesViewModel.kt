package com.stellar.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stellar.data.Product
import com.stellar.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject

sealed interface FavoriteProductsState {
    data class Success(val products : List<Product>) : FavoriteProductsState
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
        catch (e : IOException){
            favoriteProductsState = FavoriteProductsState.Error
        }
    }

    suspend fun updateFavoriteProducts(){
        viewModelScope.launch {
            try {
                favoriteProductsState = FavoriteProductsState.Loading
                val products = repository.getFavoriteProducts()
                favoriteProductsState = FavoriteProductsState.Success(products)
            } catch (e: IOException) {
                favoriteProductsState = FavoriteProductsState.Error
            }
        }
    }



    init {
        viewModelScope.launch {
            getFavoriteProducts()
        }
    }




}