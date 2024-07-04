package com.stellar.components.Buttons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun BackButton(navController : NavController){
    IconButton(
        onClick = {
            navController.navigateUp()
        }) {
        // TODO add with profile picture
        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
    }
}