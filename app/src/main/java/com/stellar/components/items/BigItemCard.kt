package com.stellar.components.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.stellar.R

@Composable
fun BigItemCard(imgSrc : Int, itemName : String, itemSeller : String, itemPrice : String, favorite: Boolean = false){



    val favorite by remember {
        mutableStateOf(favorite)
    }


    Column(


        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Image(painter = painterResource(id = imgSrc), contentDescription = "Item image")
        Text(text = itemName)
        Text(text = itemSeller)
        Text(text = itemPrice)
    }


}


@Preview
@Composable
fun ItemCardPreview(){
    BigItemCard(imgSrc = R.drawable.belts, "Belt", "Gucci", "1000")
}