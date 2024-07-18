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
fun NewArrivals(products : List<Product>, onProductClick : (Int) -> Unit, onFavorite : (Int) -> Unit, onSeeAll : () -> Unit) {



    ItemColumn(
        modifier = Modifier.padding(top = 20.dp, start = 16.dp, end = 16.dp),
        products = products,
        onClick = onProductClick,
        onFavorite = onFavorite,
        header = {
            Column {
                Banner()
                ArrivalsRow(
                    onSeeAll = {onSeeAll()}
                )
            }

    })
}


