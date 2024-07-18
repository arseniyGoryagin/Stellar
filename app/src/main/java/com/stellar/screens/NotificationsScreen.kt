package com.stellar.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.stellar.components.TopBars.NotificationsTopBar


@Composable
fun NotificationsScreen(navController : NavController){

    Scaffold(
        topBar = { NotificationsTopBar(navController = navController) },
        content = { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)){





            }
        },
    )
}