package com.stellar.components.Buttons

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ProfilePictureButton(onClick : () -> Unit){
    IconButton(
        modifier = Modifier.border(1.dp, Color.Black, shape = CircleShape ),
        onClick = onClick) {
        // TODO add with profile picture
        Icon(Icons.Filled.Person, contentDescription = "Profile")
    }

}