package com.stellar.components.Buttons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun MapButton(navController : NavController){
    IconButton(
        onClick = {
            // TODO
        }) {
        Icon(
            Icons.Outlined.Lock,
            contentDescription = "Map"
        )
    }
}