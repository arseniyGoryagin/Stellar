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


    // FIlter
    var searchFilter : SearchFilter by mutableStateOf(SearchFilter())
        private set

    var popularProductsState : PopularProductsState by mutableStateOf(PopularProductsState.Loading)

    var latestSearches : List<String> by mutableStateOf(emptyList())

    var searchActive by mutableStateOf(false)
        private set

    var currentSearch by mutableStateOf("")
        private set

    var _pager : Pager<Int, FavoriteProductWithProduct>? = null
    var products : Flow<PagingData<FavoriteProductWithProduct>>? = _pager?.flow?.cachedIn(viewModelScope)




    @OptIn(ExperimentalPagingApi::class)
    private fun getPager(query: String) : Pager<Int, FavoriteProductWithProduct>{
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                SearchProductSource(
                    repository = repository,
                    searchQuery = query)
            })

    }

    private fun getProducts(pager : Pager<Int, FavoriteProductWithProduct>? ) : Flow<PagingData<FavoriteProductWithProduct>>?{
       return  pager?.flow?.cachedIn(viewModelScope)
    }


    fun getProducts(title : String) {
        _pager = getPager(title)
        products = getProducts(_pager)
    }



    init {
        getProducts("")
        getPopularSearches()
        getLatestSearches()
    }


    /*
    viewModelScope.launch {
        try {
            searchResults = Searches.Loading
            val products = repository.getProducts(title, 10, filter = searchFilter)
            searchResults = Searches.Success(products)
            //currentSearch = title
            println("Search title\n" +title)
        }catch (e : IOException){
            searchResults = Searches.Error
        }
    }*/






    fun getPopularSearches(){
         viewModelScope.launch {
             try {
                 popularProductsState = PopularProductsState.Loading
                 val searches = repository.getPopularSearches()
                 popularProductsState =  PopularProductsState.Success(searches)
             }catch (e : HttpException){
                 popularProductsState= PopularProductsState.Error
             }
         }
    }





    private fun getLatestSearches(){
        latestSearches = repository.getLatestSearches()
    }
    fun removeLatestSearch(search : String){
        repository.removeSearch(search)
        getLatestSearches()
    }
    fun saveSearch(search: String) {
        repository.addSearch(search)
        getLatestSearches()
    }

    fun updateFilter(filter : SearchFilter) {
        searchFilter = searchFilter
    }

}