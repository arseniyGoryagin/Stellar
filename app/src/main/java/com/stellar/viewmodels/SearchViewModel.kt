package com.stellar.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.referentialEqualityPolicy
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import coil.network.HttpException
import com.stellar.data.PopularProduct
import com.stellar.data.Product
import com.stellar.data.Repository
import com.stellar.data.SearchFilter
import com.stellar.data.db.entetities.ProductEntity
import com.stellar.data.sources.SearchProductSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject





sealed interface PopularSearches{

    data class Success( val puularSearchProducts : List<PopularProduct>) : PopularSearches
    object Error : PopularSearches
    object Loading : PopularSearches

}








@HiltViewModel
class SearchViewModel@Inject constructor ( private val repository: Repository) : ViewModel() {

    var searchFilter : SearchFilter by mutableStateOf(SearchFilter())
        private set

    var popularSearches : PopularSearches by mutableStateOf(PopularSearches.Loading)

    var latestSearches : List<String> by mutableStateOf(emptyList())

    var searchActive by mutableStateOf(false)
        private set

    var currentSearch by mutableStateOf("")
        private set



    @OptIn(ExperimentalPagingApi::class)
    private fun getPager(query: String) : Pager<Int, ProductEntity>{
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {SearchProductSource(repository = repository, searchQuery = query)})

    }


    var _pager : Pager<Int, ProductEntity>? = null
    var products : Flow<PagingData<Product>>? = _pager
            ?.flow
            ?.map { pagingData ->
                pagingData.map { productEmtity ->
                    Product.toProduct(productEmtity)
                }
            }?.cachedIn(viewModelScope)



    fun getProducts(title : String) {
        println("Getting pridycs......")
        _pager = getPager(title)
        products = _pager
            ?.flow
            ?.map { pagingData ->
                pagingData.map { productEmtity ->
                    Product.toProduct(productEmtity)
                }
            }
            ?.cachedIn(viewModelScope)
        println(products)
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
                 popularSearches = PopularSearches.Loading
                 val searches = repository.getPopularSearches()
                 popularSearches =  PopularSearches.Success(searches)
             }catch (e : HttpException){
                 popularSearches = PopularSearches.Error

             }
         }
    }



    fun changeSearchActive(value : Boolean){
        searchActive = value
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

    fun updatePriceRange(range: ClosedFloatingPointRange<Float>) {
        searchFilter = searchFilter.copy(
            price_min = range.start,
            price_max = range.endInclusive
        )
        getProducts(currentSearch)
    }

}