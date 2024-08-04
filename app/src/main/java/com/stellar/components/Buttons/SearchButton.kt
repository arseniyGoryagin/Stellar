package com.stellar.components.Buttons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun SearchButton(onClick : () -> Unit){
    IconButton(
        onClick = onClick) {
        Icon(
            Icons.Outlined.Search,
            contentDescription = null
        )
    }
}

//navController.navigate("Search/${null}"