package com.stellar.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.stellar.components.TopBars.CartTopBar
import com.stellar.components.items.CartCard
import com.stellar.components.screens.ErrorScreen
import com.stellar.components.screens.LoadingScreen
import com.stellar.data.types.CartProductWithProduct
import com.stellar.ui.theme.Grey170
import com.stellar.ui.theme.Grey204
import com.stellar.ui.theme.PurpleFont
import com.stellar.viewmodels.CartProductsState
import com.stellar.viewmodels.CartViewModel

@Composable
fun CartScreen(viewModel: CartViewModel, navController: NavHostController){

    var cartProducts = viewModel.cartProducts


    var onDelete = { id : Long ->
        viewModel.removeFromCart(id)
    }

    var onRemoveQty ={ id : Long ->
        viewModel.removeItemQty(id)
    }

    var onAddQty = { id : Long ->
        viewModel.addItemQty(id)
    }

    var onCheckOut = {
        navController.navigate("Payment")
    }

    LaunchedEffect(Unit) {
        viewModel.updateCartProducts()
    }

    Scaffold(
        topBar = {
            CartTopBar(onBackClick = {navController.navigateUp()})
                 },
    content = { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)){

        when(cartProducts) {
            is CartProductsState.Error -> ErrorScreen(message = "Couldnt get cart products")
            CartProductsState.Loading -> LoadingScreen()
            is CartProductsState.Success -> {
                CartContent(
                    products = cartProducts.products,
                    onDelete = onDelete,
                    onCheckOut = onCheckOut,
                    onRemoveQty = onRemoveQty,
                    onAddQty = onAddQty
                )}
        }
    }
    }
    )
}


@Composable
fun CartContent(products : List<CartProductWithProduct>,
                onCheckOut : () -> Unit,
                onDelete : (Long) -> Unit,
                onRemoveQty : (Long) -> Unit, onAddQty : (Long) -> Unit ){


    if(products.isEmpty()){
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "No products in cart",
                color = Grey170,
                textAlign = TextAlign.Center
            )
        }
    }
    else{
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LazyColumn {
            items(products.size) { index ->
                val cartProduct = products[index].cartProduct
                val qty = cartProduct.qty
                val product = products[index].product

                CartCard(
                    imgSrc = product.images[0],
                    productName = product.title,
                    productQty = qty,
                    productPrice = product.price,
                    cartProductId = cartProduct.id,
                    onDelete = onDelete,
                    onStepDownCb = onRemoveQty,
                    onStepUpCb = onAddQty,
                    productColor = "Red",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(horizontal = 16.dp)
                )
                HorizontalDivider(
                    color = Grey204,
                    thickness = 1.dp,
                    modifier = Modifier.padding(16.dp)
                )
            }

        }
        Button(
            onClick = {
                onCheckOut()
            },
            colors = ButtonDefaults.buttonColors(containerColor = PurpleFont),
            modifier = Modifier
                .padding(top = 16.dp, start = 24.dp, end = 24.dp)
                .fillMaxWidth()
        )
        {
            Text(
                text = "Check out",
                fontSize = 18.sp,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }
    }

    }

}