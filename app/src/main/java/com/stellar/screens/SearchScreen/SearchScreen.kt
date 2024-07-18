package com.stellar.screens.SearchScreen

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.os.persistableBundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.stellar.components.Buttons.BackButton
import com.stellar.components.Buttons.NotificationButton
import com.stellar.components.TopBars.SearchInput
import com.stellar.components.TopBars.SearchToopBar
import com.stellar.components.columns.ItemColumn
import com.stellar.components.items.BigItemCard
import com.stellar.components.items.SearchItemCard
import com.stellar.components.screens.ErrorScreen
import com.stellar.components.screens.LoadingScreen
import com.stellar.data.Product
import com.stellar.screens.SearchScreen.components.LatestSearchesTextRow
import com.stellar.screens.SearchScreen.components.SearchTagRow
import com.stellar.ui.theme.Grey170
import com.stellar.viewmodels.PopularSearches
import com.stellar.viewmodels.SearchViewModel
import com.stellar.viewmodels.Searches
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter





@Composable
fun SearchScreen(viewModel: SearchViewModel, onFilter : () -> Unit,  navController: NavController, searchString : String?){



    var isActive by remember {
        if(searchString != null) {
            mutableStateOf(true)
        }else{
            mutableStateOf(false)
        }
    }

    searchString?.let {
        LaunchedEffect(key1 = searchString) {
            viewModel.getProducts(searchString)
        }
    }

    println("Search strung + = " + searchString)



    Scaffold (
        topBar =  {
            SearchTopBar(
                navController = navController,
                onFilter = onFilter,
                onFocuse = {
                    if(it){isActive = true}
                },
                onSearch = {
                    viewModel.saveSearch(it)
                    viewModel.getProducts(it)
                },
                onValueChanged = {
                    viewModel.getProducts(it)
                },
                searchString = searchString
                )
        },

    content = {padding ->
        Box(modifier = Modifier.padding(padding)) {
            if (!isActive) {
                SearchSuggestionsContent(viewModel, navController)
            } else {
                val searchResults = viewModel.searchResults
                when (searchResults) {
                    Searches.Error -> ErrorScreen(message = "Error loading products")
                    Searches.Loading -> LoadingScreen()
                    is Searches.Success -> SearchResults(
                        products = searchResults.product,
                        onItemClick = { itemId ->
                            navController.navigate("Product/$itemId")
                        })
                }
            }
        }
    }
    )



}


@Composable
fun SearchResults(products : List<Product>, onItemClick :(Int) -> Unit){
    if (products.isEmpty()) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "No products found, try changing the filters",
                color = Grey170,
                textAlign = TextAlign.Center
            )
        }
    } else {
        ItemColumn(products = products, onFavorite = { /*TODO*/ }, onClick = onItemClick, modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp))
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(navController : NavController, searchString : String?, onFilter : () -> Unit, onFocuse : (Boolean) -> Unit, onSearch : (String) -> Unit, onValueChanged : (String) -> Unit){

    var  searchInput by remember {
        if(searchString != null){
            mutableStateOf(searchString)
        }else{
        mutableStateOf("")
        }
    }

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        ),
        title= {
            SearchInput(
                placeholder = "Cheap Bags",
                onFilter = onFilter,
                value = searchInput,
                onValueChanged =
                { search ->
                    onValueChanged(search)
                    searchInput = search
                },
                onFocusChanged = {it
                    onFocuse(it.isFocused)
                },
                onSearch = { search ->
                    onSearch(search)
                }

            )
        },
        navigationIcon = {
            BackButton(navController = navController)
        },
        actions={
            NotificationButton(navController)
        }
    )
}







