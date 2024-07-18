package com.stellar.screens


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.stellar.components.BottomNavigation.BottomNavigationBar
import com.stellar.components.TopBars.SettingsTopBar

@Composable
fun SettingsScreen(navController : NavController){

    Scaffold(
        topBar = { SettingsTopBar(navController = navController) },
        content = { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)){





            }
        },

    )




}