package com.stellar.screens.HomeScreen.HomeContent

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stellar.R
import com.stellar.components.columns.ItemColumn
import com.stellar.ui.components.screens.ErrorScreen
import com.stellar.ui.theme.Blue51
import com.stellar.ui.theme.Grey204
import com.stellar.ui.theme.PurpleFont
import com.stellar.viewmodels.NewArrivalsState

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeContent(
    newArrivalsState: NewArrivalsState,
    onProductClick: (Int) -> Unit,
    onSeeAll: () -> Unit,
    onProductFavorite: (Int) -> Unit,
    onProductDeFavorite: (Int) -> Unit,
    onRefresh: () -> Unit){


    val refreshState = rememberPullRefreshState(
        refreshing = newArrivalsState is NewArrivalsState.Loading,
        onRefresh = onRefresh)



    when(newArrivalsState){
        is NewArrivalsState.Success -> {
                ItemColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp, end = 16.dp)
                        .pullRefresh(refreshState),
                    products = newArrivalsState.products,
                    onClick = onProductClick,
                    onFavorite = onProductFavorite,
                    onDeFavorite = onProductDeFavorite,
                    header =  {
                        Column {
                            Spacer(modifier = Modifier.height(20.dp))
                            Banner()
                            ArrivalsRow(
                                onSeeAll = onSeeAll
                            )
                        }
                    }
                )

        }
        is NewArrivalsState.Error -> {
            ErrorScreen(message = "Error loading products\n${newArrivalsState.e}", onRefresh)
        }
        NewArrivalsState.Loading -> {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }
    }




}




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

@Composable
fun ArrivalsRow(onSeeAll : () -> Unit){

    Row(

        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Text(
            text = "New Arrivals",
            modifier = Modifier.padding(16.dp),
            fontSize = 20.sp
        )
        Text(
            text = "See All",
            modifier = Modifier
                .padding(16.dp)
                .clickable { onSeeAll() },
            fontSize = 16.sp,
            color = PurpleFont
        )

    }
}



