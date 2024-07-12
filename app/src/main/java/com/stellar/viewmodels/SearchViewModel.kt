package com.stellar.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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







@HiltViewModel
class SearchViewModel@Inject constructor ( private val repository: Repository) : ViewModel() {

    var searchResults : Searches by mutableStateOf(Searches.Loading)
        private set

    var searchFilter : SearchFilter by mutableStateOf(SearchFilter())
        private set

    var searchActive by mutableStateOf(false)
        private set

    private var lastSearch : String = ""



    fun getProducts(title : String){
        viewModelScope.launch {
                try {
                    searchResults = Searches.Loading
                    val products = repository.getProducts(title, 10, filter = searchFilter)
                    searchResults = Searches.Success(products)
                    lastSearch = title

                }catch (e : IOException){
                    searchResults = Searches.Error
                }
        }
    }

    init {
        getProducts("")
    }


    fun changeSearchActive(value : Boolean){
        searchActive = value
    }



    fun getLatestSearches() : List<String>{
        return listOf("Helo", "Byr", "Hie")
    }

    fun updatePriceRange(range: ClosedFloatingPointRange<Float>) {
        searchFilter = searchFilter.copy(
            price_min = range.start,
            price_max = range.endInclusive
        )
        getProducts(lastSearch)
    }
}