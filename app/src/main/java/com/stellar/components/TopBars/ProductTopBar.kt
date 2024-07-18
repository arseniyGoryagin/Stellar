package com.stellar.components.TopBars

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.stellar.R
import com.stellar.components.Buttons.BackButton
import com.stellar.components.Buttons.NotificationButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductTopBar(navController : NavController, modifier: Modifier){
    CenterAlignedTopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        title= {
            Text(text = "Product Detail")
        },
        navigationIcon = {
            BackButton(navController = navController)
        },
        actions={
            // settings button
            IconButton(onClick = { navController.navigate("Cart")  }) {
                Icon(painterResource(id = R.drawable.bag_personal_outline), contentDescription = null )
            }
        }
    )



}