package com.stellar.components.TopBars

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.stellar.components.Buttons.BackButton
import com.stellar.components.Buttons.MapButton
import com.stellar.components.Buttons.NotificationButton
import com.stellar.components.Buttons.ProfilePictureButton
import com.stellar.components.Buttons.SearchButton
import com.stellar.components.Input.SearchBar
import com.stellar.constants.NavItems

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(navController : NavController){


    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        ),
        title= {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ){
                        Text("Hi! User1234",
                            fontSize = 20.sp)
                        Text("Let's go shopping ",
                            fontSize = 14.sp)
                    }
        },
        navigationIcon = {
                    ProfilePictureButton(){navController.navigate("My Profile")}
        },
        actions={
                    SearchButton(navController)
                    NotificationButton(navController)
        }
    )



}