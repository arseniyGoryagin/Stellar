package com.stellar.ui.screens


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.stellar.R
import com.stellar.components.TopBars.FavoriteTopBar
import com.stellar.components.columns.ItemColumn
import com.stellar.data.types.FavoriteProductWithProduct
import com.stellar.ui.components.screens.ErrorScreen
import com.stellar.ui.components.screens.LoadingScreen
import com.stellar.ui.theme.Grey170
import com.stellar.ui.theme.PurpleFont
import com.stellar.viewmodels.FavoriteProductsState
import com.stellar.viewmodels.FavoritesViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


sealed interface SortBY{
    object Cheapest : SortBY
    object MostPopular : SortBY
    object All : SortBY
    object Latest : SortBY
}


object selecetdIndex {

    const val ALL = 0
    const val LATEST = 1
    const val MOSTPOPULAR = 2
    const val CHEAPEST = 3

}


@Composable
fun FavoriteScreen(viewModel: FavoritesViewModel, navController : NavController) {



    var sortBy : SortBY by remember {
        mutableStateOf(SortBY.All)
    }

    val favoriteProducts : FavoriteProductsState = viewModel.favoriteProductsState

    LaunchedEffect(true) {
        viewModel.updateFavoriteProducts()
    }


    var searchString by remember {
        mutableStateOf("")
    }

    var onSearchStringChanged = { newSearchString: String ->
        searchString = newSearchString
    }

    var onNewSelected = { index : Int ->
       sortBy =  when(index){
           selecetdIndex.ALL -> SortBY.All
           selecetdIndex.LATEST -> SortBY.Latest
           selecetdIndex.CHEAPEST -> SortBY.Cheapest
           selecetdIndex.MOSTPOPULAR -> SortBY.MostPopular
           else -> SortBY.All
       }
    }

    var onFavorite ={ id : Int ->
        viewModel.addFavorite(id)

    }

    var onDeFavorite ={ id : Int  ->
        viewModel.removeFavorite(id = id)
    }

    var onItemClick = { id : Int ->
        navController.navigate("Product/$id")
    }


    Scaffold(
        topBar = { FavoriteTopBar(onNotificationClick = {navController.navigate("Notifications")}) },
        content = { innerPadding ->

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp)
                ){

                    SearchHeader(
                        onSearchChanged = onSearchStringChanged,
                        onNewSelected = onNewSelected,
                        modifier = Modifier.fillMaxWidth())

                    when(favoriteProducts){
                        is FavoriteProductsState.Success ->
                        {
                            var  products = favoriteProducts.products
                            FavoriteContent(products,
                                sortBy = sortBy,
                                onFavorite = onFavorite,
                                onDeFavorite = onDeFavorite,
                                onItemClick = onItemClick,
                                searchString = searchString
                            )
                        }
                        FavoriteProductsState.Error -> ErrorScreen(message = "Error loading favorite products", {})
                        FavoriteProductsState.Loading -> LoadingScreen()
                        else -> {}
                    }

                }
        }
    )



}


@Composable
fun SearchHeader(onSearchChanged : (String) -> Unit, onNewSelected :(Int) -> Unit, modifier: Modifier ){


    val scrollState = rememberScrollState()


    val buttons = listOf(
        "All",
        "Latest",
        "Most Popular",
        "Cheapest"
    )

    var selectedButton by remember {
        mutableStateOf("All")
    }


    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {


        SearchInput(
            placeholder = "Search something",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp),
            onValueChange = {
                onSearchChanged(it)
            }
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .horizontalScroll(scrollState),
        ) {
            buttons.forEachIndexed() { index, buttonName ->
                Button(
                    onClick = {
                        selectedButton = buttonName
                        onNewSelected(index)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedButton == buttonName) {
                            PurpleFont
                        } else {
                            Color.White
                        },
                        contentColor = if (selectedButton == buttonName) {
                            Color.White
                        } else {
                            Grey170
                        },
                    ),
                    border = if (selectedButton != buttonName) {
                        BorderStroke(1.dp, Grey170)
                    } else {
                        null
                    }
                ) {
                    Text(text = buttonName)
                }
            }

        }
    }

}



@Composable
fun FavoriteContent(products  : List<FavoriteProductWithProduct>,
                    sortBy : SortBY,
                    searchString : String?,
                    onFavorite : (Int) -> Unit,
                    onDeFavorite : (Int) -> Unit,
                    onItemClick :(Int) -> Unit ){

    var filteredProducts = products

    if(products.isEmpty()){
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = stringResource(id = R.string.noFavoriteProductsText),
                color = Grey170,
                textAlign = TextAlign.Center
            )
        }

    }
    else {


        if (searchString != null) {
            filteredProducts = products.filter {
                it.product.title.contains(searchString, ignoreCase = true)
            }
        }


        when (sortBy) {
            SortBY.All -> {





            }
            SortBY.Cheapest -> {
                filteredProducts = products.sortedBy {
                    it.product.price
                }

            }
            SortBY.Latest -> {
                println("Sort by lates")
                val formatter = DateTimeFormatter.ISO_DATE_TIME
                filteredProducts = products.sortedByDescending{
                    println(it.product.creationAt + "\n")
                    LocalDateTime.parse(it.product.creationAt, formatter)
                }

            }
            SortBY.MostPopular -> {
                filteredProducts = products.sortedBy {
                    // fake just emulating api doe not provide
                    it.product.description
                }
            }
        }


        ItemColumn(products = filteredProducts,
            onFavorite = onFavorite,
            onDeFavorite = onDeFavorite,
            onClick = onItemClick,)
    }


}



@Composable
fun SearchInput(placeholder :String, modifier: Modifier, onValueChange : (String) -> Unit){

    var inputValue by remember {
        mutableStateOf("")
    }


    OutlinedTextField(
        modifier = modifier,
        value = inputValue,
        placeholder = { Text(placeholder) },
        label = {},
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(245, 245, 245),
            unfocusedContainerColor = Color(245, 245, 245),
            focusedPlaceholderColor = Grey170,
            unfocusedPlaceholderColor = Grey170,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = PurpleFont,
            focusedLeadingIconColor = if(inputValue.length > 0) PurpleFont else Grey170,
            unfocusedLeadingIconColor = if(inputValue.length > 0) PurpleFont else Grey170,
        ),
        onValueChange = {
            inputValue = it
            onValueChange(it)
                        },
        shape = RoundedCornerShape(20.dp),
        leadingIcon = {Icon(Icons.Outlined.Search, contentDescription = "Search")},
    )


}