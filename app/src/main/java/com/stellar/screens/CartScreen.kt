package com.stellar.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.stellar.components.BottomNavigation.BottomNavigationBar
import com.stellar.components.TopBars.CartTopBar
import com.stellar.components.items.CartCard
import com.stellar.components.screens.ErrorScreen
import com.stellar.components.screens.LoadingScreen
import com.stellar.data.Product
import com.stellar.ui.theme.Grey170
import com.stellar.ui.theme.Grey204
import com.stellar.ui.theme.Grey241
import com.stellar.viewmodels.CartProductsState
import com.stellar.viewmodels.CartViewModel

@Composable
fun CartScreen(viewModel: CartViewModel, navHostController: NavHostController){

    val cartProducts = viewModel.cartProducts

    println(cartProducts)

    LaunchedEffect(true) {
        viewModel.updateCartProducts()
    }

    Scaffold(
        topBar = {CartTopBar(navController = navHostController)},
    content = { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)){
        when(cartProducts) {
            CartProductsState.Error -> ErrorScreen(message = "Couldnt get cart products")
            CartProductsState.Loading -> LoadingScreen()
            is CartProductsState.Success -> CartContent(cartProducts.products)
        }
    }
    }
    )
}


@Composable
fun CartContent(products : List<Product>){
    LazyColumn {
        items(products.size){index ->
            val product = products[index]
            CartCard(imgSrc = product.images[0], productName = product.title, productPrice = product.price)
            HorizontalDivider(
                color = Grey204,
                thickness = 1.dp,
            )
        }

    }
}