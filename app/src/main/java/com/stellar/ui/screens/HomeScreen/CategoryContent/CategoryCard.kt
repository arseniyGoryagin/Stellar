package com.stellar.screens.HomeScreen.CategoryContent

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.stellar.R


@Composable
fun CategoryCard(name : String, imageSrc : String, onClick : () -> Unit ){

    val modifier =  Modifier
        .fillMaxWidth()
        .height(100.dp)
        .clip(RoundedCornerShape(12.dp))
        .clickable {
            onClick()
        }


    SubcomposeAsyncImage(
        model= ImageRequest.Builder(context = LocalContext.current)
            .data(imageSrc)
            .crossfade(true)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier,
        error = {Image(
            painter = painterResource(id = R.drawable.noimage),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = modifier
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
}

