package com.stellar.components.TopBars

import android.app.Notification
import androidx.compose.foundation.background
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.stellar.components.Buttons.BackButton
import com.stellar.components.Buttons.NotificationButton
import com.stellar.components.Buttons.SettingsButton
import com.stellar.screens.SettingsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsTopBar(onBackClick : () -> Unit, onSettingsClick : () -> Unit){

    val searchExtended by remember { mutableIntStateOf(0) }


    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(

            containerColor = Color.White

        ),
        title= {
            Text(text = "Notifications")
        },
        navigationIcon = {
            BackButton(onClick = onBackClick)
        },
        actions={
            SettingsButton(onClick = onSettingsClick)
        }
    )



}