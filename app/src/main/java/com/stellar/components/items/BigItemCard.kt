package com.stellar.components.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stellar.R
import com.stellar.ui.theme.Grey170
import com.stellar.ui.theme.Grey204

@Composable
fun BigItemCard(imgSrc : Int, itemName : String, itemSeller : String, itemPrice : String, favorite: Boolean = false){



    var favorite by remember {
        mutableStateOf(favorite)
    }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Box(

            modifier = Modifier
                .width(160.dp)

        ) {
            Image(
                painter = painterResource(id = imgSrc),
                contentDescription = "Item image",
            )
            IconButton(
                onClick = {favorite  = !favorite },
                Modifier.align(Alignment.TopEnd).clip(CircleShape).background(Grey170).height(40.dp).width(40.dp)
            ) {
                Icon(
                    Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if(favorite){Color.Red}else{Color.White},
                )
            }
        }
        Text(
            text = itemName,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
            )
        Text(
            text = itemSeller,
            fontSize = 14.sp,
            color = Grey170
        )

        Text(
            text = "$" + itemPrice,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }


}


@Preview
@Composable
fun ItemCardPreview(){
    BigItemCard(imgSrc = R.drawable.belts, "Belt", "Gucci", "1000")
}