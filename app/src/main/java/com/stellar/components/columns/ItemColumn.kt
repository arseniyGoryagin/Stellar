package com.stellar.components.columns

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stellar.R
import com.stellar.components.items.BigItemCard
import com.stellar.data.Product


@Composable
fun ItemColumn(products : List<Product>,
               header: @Composable () -> Unit = {} ,
               onFavorite : (Int) -> Unit,
               onClick : (Int) -> Unit,
               modifier: Modifier = Modifier){



    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(20.dp)

    ) {

        item (span = {GridItemSpan(maxLineSpan)}){
            header()
        }

        items(products.size) { index ->


            val item = products[index]
            val imgSrc = item.images[0].replace("\"", "").replace("[", "")

            BigItemCard(
                favorite = item.favorite,
                itemName = item.title,
                itemSeller = item.category.name,
                itemPrice = item.price.toString(),
                onFavorite = {onFavorite(item.id)},
                onClick = { onClick(item.id)},
                imgSrc = imgSrc,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }


}





        /*
        items(products.chunked(2)) { itemPair ->

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                itemPair.forEach { item ->

                    // TODO fix this
                    val imgSrc = item.images[0].replace("\"", "").replace("[", "")
                    println("Image src == \n\n" + imgSrc)

                    BigItemCard(
                        itemName = item.title,
                        itemSeller = item.category.name,
                        itemPrice = item.price.toString(),
                        isFavorite = false,
                        onFavorite = {},
                        onClick = {},
                        imgSrc = imgSrc,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
        */

        

