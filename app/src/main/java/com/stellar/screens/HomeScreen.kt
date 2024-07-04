package com.stellar.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stellar.R
import com.stellar.ui.theme.Blue51
import com.stellar.ui.theme.Grey170
import com.stellar.ui.theme.Grey204
import com.stellar.ui.theme.PurpleFont


@Composable
fun HomeScreen() {


    var selectedTab by remember {
        mutableIntStateOf(0)
    }


        Column(

            modifier = Modifier.fillMaxSize()

        ){
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.White,
                contentColor = PurpleFont,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp)

            ) {
                Tab(
                    text = {
                        Text(
                            "Home",
                            fontSize = 16.sp) },
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 }
                )
                Tab(
                    text = {
                        Text("Category",
                            fontSize = 16.sp) },
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 }
                )
            }
            when(selectedTab){
                0 -> HomeContent()
                1 -> CategoryContent()
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
                .height(500.dp)
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
fun HomeContent(){
    
    /*LazyColumn(

        modifier = Modifier.padding(top = 16.dp)

    )
    {
        Banner()
        Row(

            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)


        ){
            Text(
                text = "New Arrivals",
                modifier = Modifier.padding(16.dp),
                fontSize = 20.sp
            )
            Text(
                text = "See All",
                modifier = Modifier.padding(16.dp),
                fontSize = 16.sp,
                color = PurpleFont
            )

        }

    }*/

    LazyColumn {
        item {
            Banner()
        }
        item{
            Row(

                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ){
                Text(
                    text = "New Arrivals",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 20.sp
                )
                Text(
                    text = "See All",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 16.sp,
                    color = PurpleFont
                )

            }
        }
    }
}

@Composable
fun CategoryContent(){
    Text(text = "Categories")
}