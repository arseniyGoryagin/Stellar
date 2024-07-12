package com.stellar.screens.HomeScreen.HomeContent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stellar.R
import com.stellar.components.columns.ItemColumn
import com.stellar.components.items.BigItemCard
import com.stellar.data.Product

@Composable
fun NewArrivals(products : List<Product>) {



    ItemColumn(
        modifier = Modifier.padding(top = 20.dp),
        products = products,
        onFavorite = { /*TODO*/ },
        onClick ={}, header = {

            Column {
                Banner()
                ArrivalsRow(
                    onSeeAll = {}
                )
            }

    })

    /*
    LazyColumn(modifier = Modifier.padding(top = 20.dp)) {
        item {
            Banner()
        }
        item {
            ArrivalsRow(
                onSeeAll = {}
            )
        }




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

    }*/
}


