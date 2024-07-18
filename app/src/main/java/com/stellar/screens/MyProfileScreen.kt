package com.stellar.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.stellar.components.BottomNavigation.BottomNavigationBar
import com.stellar.components.TopBars.ProfileTopBar
import com.stellar.constants.NavItems


@Composable
fun MyProfileScreen(navController: NavController){
    Scaffold(
        topBar = { ProfileTopBar(navController = navController) },
        content = { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)){





            }
        }
    )

}