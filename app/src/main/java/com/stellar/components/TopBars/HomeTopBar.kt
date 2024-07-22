package com.stellar.components.TopBars

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.stellar.components.Buttons.BackButton
import com.stellar.components.Buttons.MapButton
import com.stellar.components.Buttons.NotificationButton
import com.stellar.components.Buttons.ProfilePictureButton
import com.stellar.components.Buttons.SearchButton
import com.stellar.components.Input.SearchBar
import com.stellar.constants.NavItems
import com.stellar.viewmodels.UserState
import com.stellar.viewmodels.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(navController : NavController, userViewModel: UserViewModel){



    val userData = userViewModel.userState


    // TODO
    //val profilePicture = userViewModel.userData.value?.profilePhoto




    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        ),
        title= {

                    when(userData){
                        UserState.Error -> {Text("Error")}
                        UserState.Loading -> {
                            CircularProgressIndicator()
                        }
                        is UserState.Success -> {
                            Column(
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Center
                            ){
                                Text(text = "Hi! " + userData.userData.name,
                                    fontSize = 20.sp)
                                Text("Let's go shopping ",
                                    fontSize = 14.sp)
                            }
                        }

                        UserState.Idle ->CircularProgressIndicator()
                    }

        },
        navigationIcon = {

            when(userData){
                UserState.Error -> {Text("Error")}
                UserState.Loading -> { CircularProgressIndicator()}
                is UserState.Success -> ProfilePictureButton(imgSrc = userData.userData.avatar, onClick = {navController.navigate("My Profile")})
                UserState.Idle -> {CircularProgressIndicator()}
            }
        },
        actions={
            SearchButton(navController)
            NotificationButton(navController)
        }
    )



}