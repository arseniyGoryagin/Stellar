package com.stellar.screens.HomeScreen.HomeContent

import androidx.compose.runtime.Composable
import com.stellar.components.screens.ErrorScreen
import com.stellar.components.screens.LoadingScreen
import com.stellar.viewmodels.HomeViewModel
import com.stellar.viewmodels.NewArrivalsState

@Composable
fun HomeContent(viewModel: HomeViewModel){

    val newArrivalsState = viewModel.newArrivalsState

    when(newArrivalsState){
        is NewArrivalsState.Success -> NewArrivals(newArrivalsState.products)
        NewArrivalsState.Error -> ErrorScreen(message = "Error loading products")
        NewArrivalsState.Loading -> LoadingScreen()
    }
}


