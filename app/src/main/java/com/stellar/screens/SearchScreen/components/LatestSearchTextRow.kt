package com.stellar.screens.SearchScreen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.stellar.ui.theme.PurpleFont

@Composable
fun LatestSearchesTextRow(onClear : () -> Unit, modifier: Modifier){

    Row(

        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier

    ){
        Text("Latest Searches",)
        Text(
            text = "Clear All",
            modifier = Modifier.clickable { onClear },
            color = PurpleFont
        )
    }


}
