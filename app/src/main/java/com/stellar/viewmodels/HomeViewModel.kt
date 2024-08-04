package com.stellar.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stellar.data.Category
import com.stellar.data.types.Product
import com.stellar.data.Repository
import com.stellar.data.types.FavoriteProductWithProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject




sealed interface NewArrivalsState {
    data class Success(val products : List<FavoriteProductWithProduct>): NewArrivalsState
    object Error : NewArrivalsState
    object Loading : NewArrivalsState
}


sealed interface CategoriesState{
    data class Success(val categories : List<Category>): CategoriesState
    object Error : CategoriesState
    object Loading : CategoriesState
}


@HiltViewModel
class HomeViewModel @Inject constructor(private val repository : Repository) : ViewModel(){

    var newArrivalsState : NewArrivalsState by mutableStateOf(NewArrivalsState.Loading)
        private set
    var categoriesState : CategoriesState by mutableStateOf(CategoriesState.Loading)
        private set

    init {
        viewModelScope.launch {
            getNewArrivals()
            getCategories()
        }
    }


    suspend fun getNewArrivals(){
            try {
                val products = repository.getNewArrivals()
                newArrivalsState = NewArrivalsState.Success(products)
            }
            catch(e : IOException){
                newArrivalsState = NewArrivalsState.Error
            }
    }

    suspend fun getCategories(){
        try {
            val categories = repository.getCategories()
            categoriesState = CategoriesState.Success(categories)
        }
        catch(e : IOException){
            categoriesState = CategoriesState.Error
        }
    }

    fun addFavorite(id : Int){
        viewModelScope.launch {
            val updatedProducts : List<FavoriteProductWithProduct> = (newArrivalsState as NewArrivalsState.Success).products.map {
                if(id == it.product.id){
                    it.favorite = true
                }
                it
            }

            newArrivalsState = NewArrivalsState.Success(updatedProducts)

            repository.addFavorite(id)

        }
    }

    fun removeFavorite(id : Int){

        viewModelScope.launch {
            val updatedProducts: List<FavoriteProductWithProduct> =
                (newArrivalsState as NewArrivalsState.Success).products.map {
                    if (id == it.product.id) {
                        it.favorite = false
                    }
                    it
                }


            newArrivalsState = NewArrivalsState.Success(updatedProducts)

            repository.removeFavorite(id)
        }

    }




}