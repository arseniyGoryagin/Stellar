package com.stellar.components.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ErrorScreen(message : String){
    Text("Error loading" + message)
}