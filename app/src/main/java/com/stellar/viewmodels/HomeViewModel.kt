package com.stellar.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stellar.data.types.Category
import com.stellar.data.Repository
import com.stellar.data.UserState
import com.stellar.data.types.FavoriteProductWithProduct
import com.stellar.data.types.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject




sealed interface NewArrivalsState {
    data class Success(val products : List<FavoriteProductWithProduct>): NewArrivalsState
    data class Error(val e : Exception) : NewArrivalsState
    object Loading : NewArrivalsState
}


sealed interface CategoriesState{
    data class Success(val categories : List<Category>): CategoriesState
    data class Error(val e : Exception) : CategoriesState
    object Loading : CategoriesState
}




@HiltViewModel
class HomeViewModel @Inject constructor(private val repository : Repository) : ViewModel(){

    var newArrivalsState : NewArrivalsState by mutableStateOf(NewArrivalsState.Loading)
        private set
    var categoriesState : CategoriesState by mutableStateOf(CategoriesState.Loading)
        private set
    var userState  : StateFlow<UserState> = repository.userState

    init {
        viewModelScope.launch {
            //updateUserState()
            getNewArrivals()
            getCategories()
        }
    }

    suspend fun updateUserState(){
        repository.updateUserState()
    }

    suspend fun getNewArrivals(){
            try {
                val products = repository.getNewArrivals()
                newArrivalsState = NewArrivalsState.Success(products)
            }
            catch(e : Exception){
                println("EException in arrivals  ++ + " + e.localizedMessage)
                newArrivalsState = NewArrivalsState.Error(e)
            }
    }

    suspend fun getCategories(){
        try {
            val categories = repository.getCategories()
            categoriesState = CategoriesState.Success(categories)
        }
        catch(e : Exception){
            println("EException in Categories")
            categoriesState = CategoriesState.Error(e)
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

    fun updateNewArrivals() {
        viewModelScope.launch {
            getNewArrivals()
        }
    }

}