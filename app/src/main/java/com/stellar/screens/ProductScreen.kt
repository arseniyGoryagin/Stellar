package com.stellar.screens

import android.text.Layout
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsStartWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.stellar.R
import com.stellar.components.TopBars.ProductTopBar
import com.stellar.components.screens.ErrorScreen
import com.stellar.components.screens.LoadingScreen
import com.stellar.data.Product
import com.stellar.ui.theme.Grey170
import com.stellar.ui.theme.PurpleFont
import com.stellar.viewmodels.ProductState
import com.stellar.viewmodels.ProductViewModel

@Composable
fun ProductScreen(productId : Int?, viewmodel : ProductViewModel,  navController: NavController){

    if(productId == null){
        return Text(text = "No product")
    }

    LaunchedEffect(productId) {
        println("Item id = " + productId)
        viewmodel.getProduct(productId)
    }

    val productState = viewmodel.productState

    Box{
        when(productState){
            ProductState.Error -> ErrorScreen(message = "Error cant oad product")
            ProductState.Loading -> LoadingScreen()
            is ProductState.Success -> ProductContent(productState.product, onAddToCart = { productId ->
                viewmodel.addProductToCart(productId)
            })
        }
        ProductTopBar(
            navController = navController,
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Composable
fun ProductContent(product: Product, onAddToCart: (Int) -> Unit){

    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(product.images[0])
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.75f),
            error = {
                Image(
                    painter = painterResource(id = R.drawable.noimage),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )
            },
            loading = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        )

        // Overlay Box

        Box(
            modifier = Modifier
                .fillMaxWidth()
                //.fillMaxHeight(0.5f)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(40.dp))
                .background(Color.White)
        ) {
            Column() {
                Text(product.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(top = 20.dp, start = 16.dp)
                )
                Text("Description",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(top = 20.dp, start = 16.dp)
                )
                Text(product.description,
                    fontSize = 16.sp,
                    color = Grey170,
                    modifier = Modifier
                        .padding(top = 20.dp, start = 16.dp, end = 16.dp)
                )

                    PriceRow(

                        product = product,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp, start = 16.dp),
                        onAddToCart = onAddToCart

                    )


            }

        }
    }

}


@Composable
fun PriceRow(product: Product, modifier: Modifier = Modifier, onAddToCart : (Int) -> Unit){

    val price = AnnotatedString.Builder("$" + product.price)
    price.addStyle(SpanStyle(color = PurpleFont), 0,1)
    price.addStyle(SpanStyle(color = Color.Black), start = 1, end = price.length)



    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier

    ) {
        Text(
            text = price.toAnnotatedString(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier,
        )
        Button(
            onClick = {onAddToCart(product.id)},
            modifier = Modifier.padding(end = 16.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.bag_personal_outline), contentDescription = null )
            Text(text = "Add to cart")
        }
    }
}