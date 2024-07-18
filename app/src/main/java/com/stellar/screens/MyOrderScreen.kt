package com.stellar.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.stellar.components.BottomNavigation.BottomNavigationBar
import com.stellar.components.TopBars.OrderTopBar
import com.stellar.constants.NavItems


@Composable
fun MyOrderScreen (navController: NavController){
    Scaffold(modifier = Modifier.background(Color.White),
        topBar = { OrderTopBar(navController = navController) },
        content = { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding).background(Color.White)){




            }
        }
    )

}