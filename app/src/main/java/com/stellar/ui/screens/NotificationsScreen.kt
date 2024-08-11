package com.stellar.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.stellar.components.TopBars.NotificationsTopBar
import com.stellar.data.db.entetities.NotificationEntity
import com.stellar.ui.components.screens.NotificationItem
import com.stellar.ui.theme.Grey170
import com.stellar.ui.theme.Grey241
import com.stellar.viewmodels.NotificationsViewModel


@Composable
fun NotificationsScreen(navController : NavController, viewModel : NotificationsViewModel){


    val notifications : LazyPagingItems<NotificationEntity> = viewModel.notifications.collectAsLazyPagingItems()


    Scaffold(
        topBar = { NotificationsTopBar(onBackClick = {navController.navigateUp()}, onSettingsClick = {navController.navigate("Settings")}) },
        content = { innerPadding ->
            NotificationContent(
                notifications = notifications,
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
                    .fillMaxSize()
            )
        },
    )
}



@Composable
fun NotificationContent(notifications : LazyPagingItems<NotificationEntity>, modifier: Modifier = Modifier){

    println("Notifcations items size ==== " + notifications.itemCount)
    
    if(notifications.itemCount == 0){
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "No notifications",
                color = Grey170,
                textAlign = TextAlign.Center
            )
        }
    }
    else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        )
        {
            items(notifications.itemCount) { index ->
                val item = notifications[index]
                if (item != null) {
                    NotificationItem(
                        name = item.name,
                        description = item.description,
                        icon = {
                            Icon(
                                Icons.Outlined.ShoppingCart,
                                contentDescription = null
                            )
                        },)
                }
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Grey241)
            }



            /*
            if(notifications.loadState is LoadState.Loading){
                item{
                    CircularProgressIndicator()
                }
            }*/
        }
    }
}


/*
fun mapToResId(icon: Int): Int{
    return when(icon){

        else -> {Icons.Outlined.ShoppingCart}
    }
}*/





@Preview
@Composable
fun ssoo(){
    //NotificationsScreen(navController = rememberNavController())
}

