package com.stellar.components.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.stellar.R
import com.stellar.ui.theme.Grey170

@Composable
fun BigItemCard(
    modifier: Modifier,
    itemName: String,
    itemSeller: String,
    itemPrice: String,
    favorite: Boolean = false,
    onFavorite: () -> Unit,
    onDeFavorite : ()-> Unit,
    onClick: () -> Unit,
    imgSrc: String,
){





    Column(

        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.clickable {
            onClick()
        }

    ) {

        ImageBox(imgSrc = imgSrc, onFavorite = onFavorite, initialFavorite = favorite, onDeFavorite = onDeFavorite)
        Text(
            text = itemName,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
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



@Composable
fun ImageBox(imgSrc : String,onFavorite: () -> Unit, onDeFavorite : () -> Unit,  initialFavorite: Boolean){

    var favorite by remember {
        mutableStateOf(initialFavorite)
    }

    Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .aspectRatio(0.90F)
    ) {
            SubcomposeAsyncImage(
                model= ImageRequest.Builder(context = LocalContext.current)
                    .data(imgSrc)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = {Image(
                    painter = painterResource(
                        id = R.drawable.noimage),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )},
                loading = {
                    Box(

                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            )
        IconButton(
            onClick = {
                if(!favorite){
                    onFavorite()
                }else{onDeFavorite()}
                favorite  = !favorite
            },
            Modifier
                .align(Alignment.TopEnd)
                .clip(CircleShape)
                .background(Grey170)
                .height(40.dp)
                .width(40.dp)
        ) {
            Icon(
                if(favorite){Icons.Filled.Favorite}else{Icons.Outlined.FavoriteBorder},
                contentDescription = "Favorite",
                tint = if(favorite){Color.Red}else{Color.White},
            )
        }
    }


}


@Preview
@Composable
fun ItemCardPreview(){
  //  BigItemCard(imgSrc = R.drawable.belts, "Belt", "Gucci", "1000")
}