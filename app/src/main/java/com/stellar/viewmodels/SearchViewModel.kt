package com.stellar.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.stellar.data.Product
import com.stellar.data.Repository
import com.stellar.data.SearchFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject



sealed interface Searches{

    data class Success( val product : List<Product>) : Searches
    object Error : Searches
    object Loading : Searches

}


sealed interface PopularSearches{

    data class Success( val puularSearchProducts : List<Product>) : PopularSearches
    object Error : PopularSearches
    object Loading : PopularSearches

}








@HiltViewModel
class SearchViewModel@Inject constructor ( private val repository: Repository) : ViewModel() {

    var searchResults : Searches by mutableStateOf(Searches.Loading)
        private set

    var searchFilter : SearchFilter by mutableStateOf(SearchFilter())
        private set

    var popularSearches : PopularSearches by mutableStateOf(PopularSearches.Loading)

    var latestSearches : List<String> by mutableStateOf(emptyList())

    var searchActive by mutableStateOf(false)
        private set

    var currentSearch by mutableStateOf("")
        private set



    fun getProducts(title : String){
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
        }
    }


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

    init {
        getProducts("")
        getPopularSearches()
        getLatestSearches()
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