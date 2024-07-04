package com.stellar.components.TopBars

import androidx.compose.foundation.background
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.stellar.components.Buttons.BackButton
import com.stellar.components.Buttons.MapButton
import com.stellar.components.Buttons.NotificationButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderTopBar(navController : NavController){

    val searchExtended by remember { mutableIntStateOf(0) }


    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(

            containerColor = Color.White

        ),
        title= {
            Text(text = "My Order")
        },
        navigationIcon = {
        },
        actions={
            MapButton(navController)
        }
    )



}