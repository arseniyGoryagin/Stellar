package com.stellar.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import coil.network.HttpException
import com.stellar.data.PopularProduct
import com.stellar.data.types.Product
import com.stellar.data.Repository
import com.stellar.data.db.entetities.ProductEntity
import com.stellar.data.sources.SearchProductSource
import com.stellar.data.types.FavoriteProductWithProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject





sealed interface PopularProductsState{

    data class Success( val popularProducts : List<PopularProduct>) : PopularProductsState
    object Error : PopularProductsState
    object Loading : PopularProductsState

}



data class SearchFilter(
    val categoryId : Int? = null,
    val priceRange : ClosedFloatingPointRange<Float> = MIN_RANGE..MAX_RANGE,
    val location : String? = null,
    val color : String? = null
){
    companion object{
        const val MAX_RANGE : Float= 10000F
        const val MIN_RANGE: Float = 0F
    }
}




@HiltViewModel
class SearchViewModel@Inject constructor ( private val repository: Repository) : ViewModel() {

    var popularProductsState : PopularProductsState by mutableStateOf(PopularProductsState.Loading)

    var latestSearches : Flow<List<String>>? = null

    var _pager : Pager<Int, FavoriteProductWithProduct>? = null
    var _products : Flow<PagingData<FavoriteProductWithProduct>>? = _pager?.flow?.cachedIn(viewModelScope)
    var productsState = MutableStateFlow(_products)


    @OptIn(ExperimentalPagingApi::class)
    private fun getPager(query: String, searchFilter: SearchFilter) : Pager<Int, FavoriteProductWithProduct>{
        println("New paer search filter === " + searchFilter)
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                SearchProductSource(
                    repository = repository,
                    searchQuery = query,
                    searchFilter = searchFilter)
                })
    }

    private fun getProducts(pager : Pager<Int, FavoriteProductWithProduct>? ) : Flow<PagingData<FavoriteProductWithProduct>>?{
       return  pager?.flow?.cachedIn(viewModelScope)
    }


    fun getProducts(title : String, searchFilter: SearchFilter) {
        println("Updating producst + Se\n" + searchFilter)
        _pager = getPager(title, searchFilter)
        _products = getProducts(_pager)
        productsState.value = _products
    }


    init {
        viewModelScope.launch {
            getPopularSearches()
            getLatestSearches()
        }
    }



    private suspend fun getPopularSearches(){
             try {
                 popularProductsState = PopularProductsState.Loading
                 val searches = repository.getPopularSearches()
                 popularProductsState =  PopularProductsState.Success(searches)
             }catch (e : retrofit2.HttpException){
                 println("Error in popular products ")
                 popularProductsState= PopularProductsState.Error
             }
             catch (e : Exception){
                 println("Error in popular products ")
                 popularProductsState= PopularProductsState.Error
             }
    }





    private suspend fun getLatestSearches(){
        latestSearches = repository.getLatestSearches()
    }

    fun removeLatestSearch(search : String){
        viewModelScope.launch {
            repository.removeSearch(search)
        }
    }

    fun clearAllSearches(){
        viewModelScope.launch {
            repository.removeAllSearches()
        }
    }

    fun saveSearch(search: String) {
        viewModelScope.launch {
            repository.addSearch(search)
        }
    }


    fun addFavorite(id : Int){
        viewModelScope.launch {
            repository.addFavorite(id)
        }
    }

    fun removeFavorite(id  : Int){
        viewModelScope.launch {
            repository.removeFavorite(id)
        }
    }


}