package com.stellar.screens.SearchScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import com.stellar.components.items.SearchItemCard
import com.stellar.components.screens.ErrorScreen
import com.stellar.components.screens.LoadingScreen
import com.stellar.data.PopularProduct
import com.stellar.data.types.Product
import com.stellar.screens.SearchScreen.components.LatestSearchesTextRow
import com.stellar.screens.SearchScreen.components.SearchTagRow
import com.stellar.viewmodels.PopularProductsState

@SuppressLint("SuspiciousIndentation")
@Composable
fun SearchSuggestionsContent(
        popularProductsState: PopularProductsState,
        onPopularSearchClick : (Int) -> Unit,
        latestSearches : List<String>,
        onRemoveSearchSuggestion : (String) -> Unit,
        onSearchSuggestionClick: (String) -> Unit,
        onClearLatestSearches : () -> Unit){



            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)

            ) {

                item {
                    LatestSearchesTextRow(
                        onClear = onClearLatestSearches,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp, top = 16.dp)
                    )

                    latestSearches.chunked(2).forEach { searches ->
                        SearchTagRow(
                            onClear = onRemoveSearchSuggestion,
                            onClick = onSearchSuggestionClick,
                            searches = searches,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp)
                        )
                    }
                    Text(text = "Popular Searches", Modifier.padding(top = 16.dp, bottom = 8.dp))
                }


                when(popularProductsState){
                    PopularProductsState.Error -> item { ErrorScreen(message = "Error loading popular products") }
                    PopularProductsState.Loading -> item { LoadingScreen()}
                    is PopularProductsState.Success -> {
                        val products = popularProductsState.popularProducts

                        items(products.size) { index ->

                            val popularProduct = products[index]
                            val product = popularProduct.product

                            SearchItemCard(
                                productId = product.id,
                                title = product.title,
                                imgSrc = product.images[0],
                                onClick = onPopularSearchClick,
                                trailingIcon = {
                                    val backgorundColor = when(popularProduct.type){
                                        "Hot" ->{Color.Red}
                                        "Popular" -> {Color.Green}
                                        "New" -> {Color.Yellow}
                                        else -> {Color.Red}
                                    }
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(20.dp))
                                            .background(backgorundColor)
                                    ){
                                        Text(
                                            modifier = Modifier.padding(horizontal = 5.dp),
                                            text = popularProduct.type,
                                            color = Color.White
                                        )
                                    }
                                },
                                searches = "${popularProduct.searches}k searches today",
                                modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .padding(top = 16.dp)
                            )
                        }
                    }
                }

            }
}

/*

{
                                    navController.navigate("Product/${product.id}")
                                }
 */


