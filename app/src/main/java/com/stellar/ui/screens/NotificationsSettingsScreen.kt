package com.stellar.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.stellar.components.TopBars.GenericTopBar
import com.stellar.ui.components.screens.ToggleSetting
import com.stellar.ui.theme.Grey241

@Composable
fun NotificationsSettingsScreen(navController : NavController){
    Scaffold(
        topBar = { GenericTopBar(name = "Notifications", onBackClick = {navController.navigateUp()}) },
        content = { innerPadding ->
            Box(
                modifier =
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize()){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 10.dp)
                        .background(Color.White)
                        .border(2.dp, Grey241, shape = RoundedCornerShape(20.dp))


                ) {

                    ToggleSetting(name = "Payment", startToggle = true, onToggleChange = {

                    },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp).padding(top = 10.dp))
                    HorizontalDivider(color = Grey241, thickness = 2.dp, modifier = Modifier.padding(10.dp))
                    ToggleSetting(name = "Tracking", startToggle = true, onToggleChange = {

                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp))
                    HorizontalDivider(color = Grey241, thickness = 2.dp, modifier = Modifier.padding(10.dp))
                    ToggleSetting(name = "Complete Order", startToggle = true, onToggleChange = {

                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp))
                    HorizontalDivider(color = Grey241, thickness = 2.dp, modifier = Modifier.padding(10.dp))
                    ToggleSetting(name = "Notification", startToggle = true, onToggleChange = {

                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp).padding(bottom = 10.dp))

                }


            }
        },
    )
}



