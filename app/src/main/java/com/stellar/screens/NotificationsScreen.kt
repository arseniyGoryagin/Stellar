package com.stellar.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.createBitmap
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.stellar.components.NotificationItem
import com.stellar.components.TopBars.NotificationsTopBar
import com.stellar.data.db.entetities.Notification
import com.stellar.ui.theme.Grey241
import com.stellar.viewmodels.NotificationsViewModel


@Composable
fun NotificationsScreen(navController : NavController, viewModel : NotificationsViewModel){


    val notifictions = viewModel.notifications


    Scaffold(
        topBar = { NotificationsTopBar(navController = navController) },
        content = { innerPadding ->


            LazyColumn(

                modifier =
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(top = 10.dp))
            {


                    items(notifictions.size){ index ->
                        val item = notifictions[index]
                        NotificationItem(
                            name = item.name,
                            description = item.description,
                            icon = { Icon(painter = painterResource(item.icon), contentDescription = null) })
                    }






            }

        },
    )
}



@Composable
fun NotificationContent(notifications : List<Notification>){


    LazyColumn {

        items(notifications.size){





        }


    }



}





@Preview
@Composable
fun ssoo(){
    //NotificationsScreen(navController = rememberNavController())
}

