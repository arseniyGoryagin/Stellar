package com.stellar.screens.HomeScreen.HomeContent

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.navigation.NavController
import com.stellar.components.screens.ErrorScreen
import com.stellar.components.screens.LoadingScreen
import com.stellar.viewmodels.HomeViewModel
import com.stellar.viewmodels.NewArrivalsState
import com.stellar.viewmodels.UserViewModel

@Composable
fun HomeContent(viewModel: HomeViewModel, navController: NavController){




    val newArrivalsState =  viewModel.newArrivalsState

    val onSeeAll = remember(navController){
        {
            val searchString = " "
            navController.navigate("Search/$searchString"){
                launchSingleTop = true
            }
        }
    }

    val onFavorite = remember{
        { id : Int ->
            viewModel.addFavorite(id)
        }
    }

    val onDeFavorite = remember{
        { id : Int ->
            viewModel.removeFavorite(id)
        }
    }

    val onProductClick = remember(navController){
        { itemId  : Int
            -> navController.navigate("Product/$itemId")
        }
    }


    when(newArrivalsState){
        is NewArrivalsState.Success -> NewArrivals(newArrivalsState.products,
            onProductClick = onProductClick,
            onFavorite = onFavorite,
            onSeeAll =  onSeeAll,
            onDeFavorite = onDeFavorite)
        NewArrivalsState.Error -> ErrorScreen(message = "Error loading products")
        NewArrivalsState.Loading -> LoadingScreen()
    }

}


