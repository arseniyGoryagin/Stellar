package com.stellar.components.TopBars

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stellar.components.Buttons.NotificationButton
import com.stellar.components.Buttons.ProfilePictureButton
import com.stellar.components.Buttons.SearchButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(userName:  String, userImg : String, onSearch : () -> Unit, onNotifications : () -> Unit, onProfileClick : () -> Unit){




    // TODO
    //val profilePicture = userViewModel.userData.value?.profilePhoto




    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        ),
        title= {

            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            ){
                Text(text = "Hi! " + userName,
                    fontSize = 20.sp)
                Text("Let's go shopping ",
                    fontSize = 14.sp)
            }
        },
        navigationIcon = {
            ProfilePictureButton(
                imgSrc = userImg,
                onClick = onProfileClick,
                modifier = Modifier.padding(start = 16.dp))
        },
        actions={

            SearchButton(onClick = onSearch)
            NotificationButton(
                onClick = onNotifications,
                modifier = Modifier.padding(end = 16.dp))
        }
    )



}