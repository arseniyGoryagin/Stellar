package com.stellar.components.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.stellar.R
import com.stellar.ui.theme.Grey170
import okhttp3.internal.connection.RouteException


@Composable
fun SearchItemCard(
    productId : Int,
    title : String,
    searches : String,
    imgSrc : String,
    modifier: Modifier = Modifier,
    onClick : (Int) -> Unit,
    trailingIcon : @Composable () -> Unit){


    Row(

        //horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.clickable { onClick(productId) }

    ) {
        Row(

            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ){

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
                        painter = painterResource(id = R.drawable.noimage),
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
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 20.sp
                )
                Text(
                    text = searches,
                    fontSize = 16.sp,
                    color = Grey170
                )
            }

        }
        trailingIcon()
    }
}




