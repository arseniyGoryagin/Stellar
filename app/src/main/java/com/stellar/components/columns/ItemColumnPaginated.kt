package com.stellar.components.columns

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.internal.isLiveLiteralsEnabled
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.stellar.R
import com.stellar.components.items.BigItemCard
import com.stellar.data.Product


@Composable
fun ItemColumnPaginated(
    products : LazyPagingItems<Product>,
    header: @Composable () -> Unit = {} ,
    onFavorite : (Int) -> Unit,
    onDeFavorite : (Int) -> Unit,
    onClick : (Int) -> Unit,
    modifier: Modifier = Modifier){



    Box() {
        if (products.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator()
        }else{
            LazyVerticalGrid(
                modifier = modifier,
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(20.dp)

            ) {

                item(span = { GridItemSpan(maxLineSpan) }) {
                    header()
                }


                items(products.itemCount) { index ->
                    val item = products[index]
                    if (item != null) {
                        val imgSrc = item.images[0].replace("\"", "").replace("[", "")

                        BigItemCard(
                            favorite = item.favorite,
                            itemName = item.title,
                            itemSeller = item.category.name,
                            itemPrice = item.price.toString(),
                            onFavorite = { onFavorite(item.id) },
                            onClick = { onClick(item.id) },
                            onDeFavorite = { onDeFavorite(item.id) },
                            imgSrc = imgSrc,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }


                item {
                    if(products.loadState.append is LoadState.Loading){
                        CircularProgressIndicator()
                    }
                }
            }


        }



    }
}
