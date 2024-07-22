package com.stellar.components.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.internal.StabilityInferred
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.stellar.R
import okhttp3.internal.connection.RouteException


@Composable
fun SearchItemCard(title : String, imgSrc : String, modifier: Modifier = Modifier, onClick : () -> Unit){
    Row(

        //horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable { onClick() }

    ) {
        SubcomposeAsyncImage(
            modifier= Modifier.clip(RoundedCornerShape(20.dp)),
            model= ImageRequest.Builder(context = LocalContext.current)
                .data(imgSrc)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            error = {
                Image(
                painter = painterResource(id = R.drawable.image_broken_variant),
                contentDescription = null,
                contentScale = ContentScale.Fit,

            )
            },
            loading = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        )
        Text(
            text = title
        )
    }
}


@Preview
@Composable
fun Preview(){
    SearchItemCard(title = "Products", imgSrc = "https://i.imgur.com/qNOjJje.jpeg",
        modifier = Modifier.fillMaxWidth()){}
}


