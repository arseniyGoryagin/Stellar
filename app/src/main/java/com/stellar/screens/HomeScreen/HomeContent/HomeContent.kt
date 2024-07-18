package com.stellar.screens.HomeScreen.HomeContent

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.stellar.components.screens.ErrorScreen
import com.stellar.components.screens.LoadingScreen
import com.stellar.viewmodels.HomeViewModel
import com.stellar.viewmodels.NewArrivalsState

@Composable
fun HomeContent(viewModel: HomeViewModel, navController: NavController){

    val newArrivalsState = viewModel.newArrivalsState

    when(newArrivalsState){
        is NewArrivalsState.Success -> NewArrivals(newArrivalsState.products,
            onProductClick = { itemId -> navController.navigate("Product/$itemId")},
            onFavorite = {id -> viewModel.addFavorite(id)},
            onSeeAll = {
            val searchString = " "
            navController.navigate("Search/$searchString")
        })
        NewArrivalsState.Error -> ErrorScreen(message = "Error loading products")
        NewArrivalsState.Loading -> LoadingScreen()
    }
}


