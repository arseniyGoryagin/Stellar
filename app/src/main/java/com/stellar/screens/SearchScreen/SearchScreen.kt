package com.stellar.screens.SearchScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.stellar.components.Buttons.BackButton
import com.stellar.components.Buttons.NotificationButton
import com.stellar.components.Input.SearchInput
import com.stellar.components.columns.ItemColumnPaginated
import com.stellar.data.types.FavoriteProductWithProduct
import com.stellar.data.types.Product
import com.stellar.ui.theme.Grey170
import com.stellar.viewmodels.SearchFilter
import com.stellar.viewmodels.SearchViewModel
import kotlinx.coroutines.isActive


@Composable
fun SearchScreen(viewModel: SearchViewModel, navController: NavController, searchString : String?){



    val latestSearches = viewModel.latestSearches
    val popularProductsState = viewModel.popularProductsState
    val productsFlow = viewModel.products
    val products : LazyPagingItems<FavoriteProductWithProduct>? = viewModel.products?.collectAsLazyPagingItems()

    val currentFilter = viewModel.searchFilter



    var showFilterBottomSheet by remember {
        mutableStateOf(false)
    }

    val currentSearchString by remember {
        mutableStateOf(searchString ?: "")
    }

    var isActive by remember {
        if(searchString != null) {
            mutableStateOf(true)
        }else{
            mutableStateOf(false)
        }
    }


    LaunchedEffect(Unit) {
        if(isActive) {
            viewModel.getProducts(currentSearchString)
        }
    }

    LaunchedEffect(key1 = currentFilter) {
        viewModel.getProducts(currentSearchString)
    }



    val onSearch = { it : String ->
            viewModel.saveSearch(it)
            viewModel.getProducts(it)
    }

    val onValueChanged = { it : String ->
            viewModel.getProducts(it)
    }


    val onFocus = { focus : Boolean ->
        if(focus){isActive = true}
    }

    val onFilter = {
        showFilterBottomSheet = true
    }

    val onNotificationPress = {
        navController.navigate("Notifications")
    }

    val onBackButtonPress = {
        navController.navigateUp()
        Unit
    }

    val onClearLatestSearches = {

    }

    val onPopularSearchClick = { id : Int ->
        navController.navigate("Product/${id}")
    }

    val onRemoveSuggestion = { sugg : String ->

    }

    val onSearchSuggestionClick = { sugg : String ->

    }


    val onSearchItemClick = { id : Int ->
        navController.navigate("Product/${id}")
    }

    val onProductFavorite = { id : Int ->

    }

    val onProductDeFavorite = { id : Int ->

    }


    val onFilterDismiss = { filter : SearchFilter->
        viewModel.updateFilter(filter)
        showFilterBottomSheet = false
    }




    Scaffold (
        topBar =  {
            SearchTopBar(
                onFilter = onFilter,
                onFocus = onFocus,
                onSearch = onSearch,
                onValueChanged = onValueChanged,
                onBackButton = onBackButtonPress,
                onNotificationClick = onNotificationPress,
                searchString = searchString
                )
        },
    content = {padding ->
        Box(modifier = Modifier.padding(padding)) {

            if (!isActive) {
                SearchSuggestionsContent(
                    latestSearches = latestSearches,
                    popularProductsState = popularProductsState,
                    onClearLatestSearches = onClearLatestSearches,
                    onPopularSearchClick = onPopularSearchClick,
                    onRemoveSearchSuggestion = onRemoveSuggestion,
                    onSearchSuggestionClick = onSearchSuggestionClick
                )
            } else {
                SearchContent(
                    products = products,
                    onItemClick = onSearchItemClick,
                    onFavorite = onProductFavorite,
                    onDeFavorite = onProductDeFavorite
                    )
            }
            if(showFilterBottomSheet){
                FilterBottomSheet(
                    onDismiss = onFilterDismiss,
                    currentSearchFilter = currentFilter)
            }
        }
    }
    )



}



@Composable
fun SearchContent(products: LazyPagingItems<FavoriteProductWithProduct>?,
                  onItemClick :(Int) -> Unit,
                  onFavorite : (Int) -> Unit,
                  onDeFavorite : (Int) -> Unit){



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
        ItemColumnPaginated(
            products = products,
            onFavorite = onFavorite,
            onDeFavorite = onDeFavorite,
            onClick = onItemClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp))
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar( searchString : String?,
                  onFocus : (Boolean) -> Unit,
                  onSearch : (String) -> Unit,
                  onValueChanged : (String) -> Unit,
                  onBackButton : () -> Unit,
                  onNotificationClick : () -> Unit,
                  onFilter: () -> Unit){


    var onSearch = { searchString : String->
        onSearch(searchString)
    }

    var onFocusChanged = { focus : FocusState ->
        onFocus(focus.isFocused)
    }

    var onValueChanged ={ search : String->
        onValueChanged(search)
    }

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        ),
        title= {
            SearchInput(
                placeholder = "Cheap Bags",
                onFilter = onFilter,
                value = searchString,
                onValueChanged = onValueChanged,
                onFocusChanged = onFocusChanged,
                onSearch = onSearch

            )
        },
        navigationIcon = {
            IconButton(
                onClick = onBackButton) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        actions={
            IconButton(
                onClick = onNotificationClick) {
                Icon(
                    Icons.Outlined.Notifications,
                    contentDescription = "Notifications"
                )
            }
        }
    )
}










