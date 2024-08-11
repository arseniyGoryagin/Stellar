package com.stellar.components.Buttons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun NotificationButton(onClick : () -> Unit, modifier: Modifier = Modifier){
    IconButton(
        onClick = onClick) {
        Icon(
            Icons.Outlined.Notifications,
            contentDescription = "Notifications"
        )
    }

}

//     navController.navigate("Notifications")