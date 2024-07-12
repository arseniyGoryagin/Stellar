package com.stellar.screens.HomeScreen.HomeContent

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.stellar.R
import com.stellar.ui.theme.Blue51
import com.stellar.ui.theme.Grey204

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Banner(){

    val pagerState = rememberPagerState(pageCount = {3})

    Column(

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        HorizontalPager(state = pagerState) { page ->


            val imgModifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(start = 16.dp, end = 16.dp)
                .clip(
                    RoundedCornerShape(16.dp)
                )


            when (page) {
                0 -> Image(painter = painterResource(id = R.drawable.clothes), contentDescription = "Clothes", contentScale = ContentScale.Crop,
                    modifier = imgModifier)

                1 -> Image(painter = painterResource(id = R.drawable.clothes2), contentDescription = "Clothes", contentScale = ContentScale.Crop,
                    modifier = imgModifier)

                2 -> Image(painter = painterResource(id = R.drawable.belts), contentDescription = "Clothes", contentScale = ContentScale.Crop,
                    modifier = imgModifier)

                else -> Text("Dont know")
            }

        }
        Row() {
            repeat(pagerState.pageCount) { iteration ->

                val color = if (pagerState.currentPage == iteration) Blue51 else Grey204

                Box(
                    modifier = Modifier
                        .padding(4.dp, top = 16.dp)
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(color)
                )
            }


        }
    }

}