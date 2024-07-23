package com.stellar.screens.SearchScreen

import android.app.appsearch.AppSearchManager.SearchContext
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
import androidx.compose.runtime.collectAsState
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
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.stellar.components.Buttons.BackButton
import com.stellar.components.Buttons.NotificationButton
import com.stellar.components.TopBars.SearchInput
import com.stellar.components.TopBars.SearchToopBar
import com.stellar.components.columns.ItemColumn
import com.stellar.components.columns.ItemColumnPaginated
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
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map


@Composable
fun SearchScreen(viewModel: SearchViewModel, navController: NavController, searchString : String?){


    searchString?.let {
        LaunchedEffect(Unit) {
            viewModel.getProducts(searchString)
        }
    }

    var isActive by remember {
        if(searchString != null) {
            mutableStateOf(true)
        }else{
            mutableStateOf(false)
        }
    }


    val onSearch = remember(viewModel){
        { it : String ->
            viewModel.saveSearch(it)
            viewModel.getProducts(it)


        }
    }
    val onValueChanged = remember(viewModel) {
        { it : String ->
            println("Getting products === " + it)
            viewModel.getProducts(it)
        }
    }



    Scaffold (
        topBar =  {
            SearchTopBar(
                navController = navController,
                onFilter = {},
                onFocuse = {
                    if(it){isActive = true}
                },
                onSearch = onSearch,
                onValueChanged = onValueChanged,
                searchString = searchString
                )
        },
    content = {padding ->
        Box(modifier = Modifier.padding(padding)) {
            if (!isActive) {
                SearchSuggestionsContent(viewModel, navController)
            } else {

                val flow = viewModel.products?.collectAsState(initial = PagingData.empty())
                
                
                val products : LazyPagingItems<Product>? = viewModel.products?.collectAsLazyPagingItems()
                println("Products value \n" + viewModel.products)
                println("Actual products \nk" + products?.loadState)

                SearchContent(products, onItemClick = {

                })
            }
        }
    }
    )



}



@Composable
fun SearchContent(products: LazyPagingItems<Product>?, onItemClick :(Int) -> Unit){



    if (products == null || products.itemCount == 0) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "No products found, try changing the filters",
                color = Grey170,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    } else {
        ItemColumnPaginated(products = products, onFavorite = { /*TODO*/ },
            onClick = onItemClick,
            onDeFavorite = {},
            modifier = Modifier
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







