package com.stellar.components.Buttons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable

@Composable
fun SettingsButton(onClick : () -> Unit) {
    IconButton(
        onClick = onClick) {
        // TODO add with profile picture
        Icon(Icons.Filled.Settings, contentDescription = "Back")
    }
}