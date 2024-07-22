package com.stellar.screens.SearchScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.stellar.components.Buttons.BackButton
import com.stellar.components.Buttons.NotificationButton
import com.stellar.components.TopBars.SearchInput

import com.stellar.components.items.SearchItemCard
import com.stellar.components.screens.ErrorScreen
import com.stellar.components.screens.LoadingScreen
import com.stellar.screens.SearchScreen.components.LatestSearchesTextRow
import com.stellar.screens.SearchScreen.components.SearchTagRow
import com.stellar.viewmodels.PopularSearches
import com.stellar.viewmodels.SearchViewModel

@SuppressLint("SuspiciousIndentation")
@Composable
fun SearchSuggestionsContent(viewModel: SearchViewModel, navController: NavController){

    var popularSearches : PopularSearches =  viewModel.popularSearches
    var latestSearchs = viewModel.latestSearches


            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)

            ) {

                item {
                    LatestSearchesTextRow(
                        onClear = {
                            //viewModel.removeAllSearches()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)

                    )

                    latestSearchs.chunked(2).forEach { searches ->
                        SearchTagRow(
                            onClear = { search ->
                                viewModel.removeLatestSearch(search)
                            },
                            onClick = { search ->
                                viewModel.changeSearchActive(true)
                                viewModel.getProducts(search)
                            },
                            searches = searches,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp))
                    }


                    Text(text = "Popular Searches", Modifier.padding(top = 16.dp, bottom = 8.dp))
                }


                when (popularSearches) {
                    PopularSearches.Error -> item { ErrorScreen(message = "Error loading popular searches") }
                    PopularSearches.Loading -> item { LoadingScreen() }
                    is PopularSearches.Success -> {

                        val products = popularSearches.puularSearchProducts


                        items(products.size) { index ->
                            val product = products[index]
                            SearchItemCard(
                                title = product.title,
                                imgSrc = product.images[0],
                                onClick = {
                                    navController.navigate("Product/${product.id}")
                                },
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


