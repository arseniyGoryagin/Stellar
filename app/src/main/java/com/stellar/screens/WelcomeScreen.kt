package com.stellar.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.navigation.NavController
import com.stellar.R
import com.stellar.ui.theme.Blue51
import com.stellar.ui.theme.Grey170
import com.stellar.ui.theme.Grey204
import com.stellar.ui.theme.PurpleFont
import com.stellar.viewmodels.UserViewModel


@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Pager(){

    val pagerState = rememberPagerState(pageCount = {3})

        Column(

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            HorizontalPager(state = pagerState) { page ->


                val imgModifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .clip(
                        RoundedCornerShape(20.dp)
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
            Text(
                text = "Variose Collections Of The Latest Products",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Text(
                text = "Urna amet, suspendisse ullamcorper ac elit diam facilisis cursus vestibulum",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp),
                color = Grey170,
                fontSize = 14.sp
            )


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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WelcomeScreen (navController: NavController, userViewModel: UserViewModel){


    LaunchedEffect(Unit) {
        userViewModel.resetState()
    }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.fillMaxSize()
    ) {
        Pager()
        Button(
            onClick = {navController.navigate("Create Account")},
            colors = ButtonDefaults.buttonColors(containerColor = PurpleFont),
            modifier = Modifier
                .padding(top = 16.dp, start = 24.dp, end = 24.dp)
                .fillMaxWidth()
        )
        {
            Text("Create an Account", fontSize = 18.sp)
        }
        Button(
            onClick = {navController.navigate("Sign In")},
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            modifier =
            Modifier.
            padding(top = 16.dp, bottom = 16.dp, start = 24.dp, end =24.dp)
        ){
            Text("Already Have an Account", fontSize = 16.sp, color = PurpleFont)
        }
    }
}