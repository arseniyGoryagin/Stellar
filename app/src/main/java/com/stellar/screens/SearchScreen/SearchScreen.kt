package com.stellar.screens.SearchScreen

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.clearCompositionErrors
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.stellar.components.columns.ItemColumn
import com.stellar.components.items.BigItemCard
import com.stellar.components.screens.ErrorScreen
import com.stellar.components.screens.LoadingScreen
import com.stellar.data.Product
import com.stellar.screens.SearchScreen.components.LatestSearchesTextRow
import com.stellar.screens.SearchScreen.components.SearchTags
import com.stellar.ui.theme.Grey170
import com.stellar.viewmodels.SearchViewModel
import com.stellar.viewmodels.Searches
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@Composable
fun SearchScreen(viewModel: SearchViewModel){

    var recentSearches by remember{
        mutableStateOf(viewModel.getLatestSearches())
    }




    Column(

        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)



    ) {


        if(!viewModel.searchActive){
        LatestSearchesTextRow(
            onClear = {
                //viewModel.removeAllSearches()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)

        )
        SearchTags(
            searches = recentSearches,
            onClick = {

            },
            onClear ={
               // viewModel.removeRecentSearch()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        }
        else{
        SearchContent(viewModel = viewModel)
        }
    }


}


@Composable
fun SearchContent(viewModel: SearchViewModel){

    val searchResults = viewModel.searchResults
    
    when(searchResults){
        Searches.Error -> ErrorScreen(message = "Error loading products")
        Searches.Loading -> LoadingScreen()
        is Searches.Success -> SearchResults(products = searchResults.product)
    }


}


@Composable
fun SearchResults(products : List<Product>){
    if(products.size == 0){
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ){
            Text(
                text = "No products found, try changing the filters",
                color = Grey170,
                textAlign = TextAlign.Center
            )

        }

    }else {
        ItemColumn(products = products, onFavorite = { /*TODO*/ }, onClick = {})
    }
}




