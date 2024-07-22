package com.stellar.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.stellar.R
import com.stellar.components.BottomNavigation.BottomNavigationBar
import com.stellar.components.Input.SearchBar
import com.stellar.components.TopBars.FavoriteTopBar
import com.stellar.components.columns.ItemColumn
import com.stellar.components.items.BigItemCard
import com.stellar.components.screens.ErrorScreen
import com.stellar.components.screens.LoadingScreen
import com.stellar.constants.NavItems
import com.stellar.ui.theme.Grey170
import com.stellar.ui.theme.PurpleFont
import com.stellar.viewmodels.FavoriteProductsState
import com.stellar.viewmodels.FavoritesViewModel


@Composable
fun FavoriteScreen(viewModel: FavoritesViewModel, navController : NavController) {


    Scaffold(
        topBar = { FavoriteTopBar(navController = navController) },
        content = { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)){

                val buttons = listOf(
                    "All",
                    "Latest",
                    "Most Popular",
                    "Cheapest"
                )

                var selectedButton by remember {
                    mutableStateOf("All")
                }

                val favoriteProducts : FavoriteProductsState = viewModel.favoriteProductsState

                LaunchedEffect(true) {
                    viewModel.updateFavoriteProducts()
                }


                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                {
                    SearchInput(
                        placeholder = "Search something",
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, bottom = 16.dp)
                    )
                    Row(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        buttons.forEachIndexed(){ index, buttonName ->
                            Button(
                                onClick = {
                                    selectedButton = buttonName
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if(selectedButton == buttonName){
                                        PurpleFont}else{Color.White},
                                    contentColor = if(selectedButton == buttonName){
                                        Color.White}else{
                                        Grey170},
                                ),
                            ) {
                                Text(text = buttonName)
                            }
                        }

                    }

                    when(favoriteProducts){
                        is FavoriteProductsState.Success ->
                        {
                            var  products = favoriteProducts.products

                            when(selectedButton){
                                "Cheapest" -> {
                                    products = products.sortedBy {
                                        it.price
                                    }

                                }
                                "Most Popular" ->{
                                    products = products.sortedBy {
                                        // fake just emulating api doe not provide
                                        it.description
                                    }
                                }
                            }


                            ItemColumn(products = products, onFavorite = {
                            viewModel.addFavorite(it)
                        },
                            onClick = { id ->
                                navController.navigate("Product/$id")
                            },
                            onDeFavorite = {
                                viewModel.removeFavorite(it)
                            })}
                        FavoriteProductsState.Error -> ErrorScreen(message = "Error loading favorite products")
                        FavoriteProductsState.Loading -> LoadingScreen()
                        else -> {}
                    }

                }



            }
        }
    )



}



@Composable
fun SearchInput(placeholder :String, modifier: Modifier){

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
        onValueChange = {inputValue = it},
        shape = RoundedCornerShape(20.dp),
        leadingIcon = {Icon(Icons.Outlined.Search, contentDescription = "Search")},
    )


}