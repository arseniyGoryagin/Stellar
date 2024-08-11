package com.stellar.components.columns

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stellar.components.items.BigItemCard
import com.stellar.data.types.FavoriteProductWithProduct
import com.stellar.data.types.Product


@Composable
fun ItemColumn(products : List<FavoriteProductWithProduct>,
               header: @Composable () -> Unit = {},
               onFavorite : (Int) -> Unit,
               onDeFavorite : (Int) -> Unit,
               onClick : (Int) -> Unit,
               modifier: Modifier = Modifier){



    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item (span = {GridItemSpan(maxLineSpan)}){
            header()
        }

        items(products.size) { index ->


            val item = products[index]
            val product = item.product
            val favorite = item.favorite
            val imgSrc = product.images[0].replace("\"", "").replace("[", "")

            BigItemCard(
                favorite = favorite,
                itemName = product.title,
                itemSeller = product.category.name,
                itemPrice = product.price.toString(),
                onFavorite = {onFavorite(product.id)},
                onClick = { onClick(product.id)},
                onDeFavorite ={ onDeFavorite(product.id)},
                imgSrc = imgSrc,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

        

