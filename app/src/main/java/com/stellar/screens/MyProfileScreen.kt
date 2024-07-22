package com.stellar.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.stellar.R
import com.stellar.components.BottomNavigation.BottomNavigationBar
import com.stellar.components.Input.TextInput
import com.stellar.components.TopBars.ProfileTopBar
import com.stellar.components.screens.ErrorScreen
import com.stellar.components.screens.LoadingScreen
import com.stellar.constants.NavItems
import com.stellar.data.User
import com.stellar.ui.theme.PurpleFont
import com.stellar.viewmodels.MyProfileViewModel
import com.stellar.viewmodels.SaveDataState
import com.stellar.viewmodels.UserState
import com.stellar.viewmodels.UserViewModel


@Composable
fun MyProfileScreen(navController: NavController, userViewModel: UserViewModel, myProfileViewModel: MyProfileViewModel){


    val savingState = myProfileViewModel.saveDataState


    LaunchedEffect(Unit) {
        myProfileViewModel.resetState()
    }

    Scaffold(
        topBar = { ProfileTopBar(navController = navController) },
        content = { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {

                when (savingState) {
                    SaveDataState.Error -> MyProfileContent(
                        userViewModel,
                        navController = navController,
                        viewModel = myProfileViewModel,
                        "Erorr saving changes",
                        null
                    )

                    SaveDataState.Idle -> MyProfileContent(
                        userViewModel,
                        navController = navController,
                        viewModel = myProfileViewModel,
                        null,
                        null
                    )

                    SaveDataState.Loading -> LoadingScreen()
                    SaveDataState.Success -> MyProfileContent(
                        userViewModel,
                        navController = navController,
                        viewModel = myProfileViewModel,
                        null,
                        "Saved changes"
                    )
                }
            }
        }
    )

}








@Composable
private fun MyProfileContent(userViewModel: UserViewModel, navController: NavController, viewModel: MyProfileViewModel, error : String?, success : String?){



    val userState = userViewModel.userState


    if(success != null){
        LaunchedEffect(Unit) {
            userViewModel.updateUserData()
        }
    }

    when(userState) {
        UserState.Error -> ErrorScreen(message = "Error laoding profile")
        UserState.Loading -> LoadingScreen()
        UserState.Idle -> ErrorScreen(message = "User not logged in")
        is UserState.Success -> {


            val userData = userState.userData


            var success by remember {
                mutableStateOf(success)
            }

            var error by remember {
                mutableStateOf(error)
            }

            var name by remember {
                mutableStateOf(userData.name)
            }

            var email by remember {
                mutableStateOf(userData.email)
            }




                        Column(
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        ) {
                            SubcomposeAsyncImage(
                                model= ImageRequest.Builder(context = LocalContext.current)
                                    .data(userData.avatar)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                error = {
                                    Image(
                                        painter = painterResource(id = R.drawable.image_broken_variant),
                                        contentDescription = null,
                                        contentScale = ContentScale.Fit,
                                        modifier = Modifier.clip(RoundedCornerShape(12.dp))
                                    )
                                },
                                loading = {
                                    Box(

                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                }
                            )

                            TextInput(
                                name = "Name",
                                startValue = userData.name,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                icon = {
                                    Icon(Icons.Filled.Person, contentDescription = null)
                                },
                                trailingIcon = { /*TODO*/ },
                                onValueChange = {
                                    name =it
                                },
                                visibleText = true
                            )
                            TextInput(
                                name = "Email",
                                startValue = userData.email,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                icon = {
                                    Icon(Icons.Filled.MailOutline, contentDescription = null)
                                },
                                trailingIcon = {
                                },
                                onValueChange = {
                                    email =it

                                },
                                visibleText = true
                            )
                            if(success != null){
                                Text(
                                    success!!,
                                    color = Color.Green
                                )
                            }
                            if(error != null){
                                Text(
                                    error!!,
                                    color = Color.Red
                                )
                            }
                            Button(
                                onClick = {
                                    viewModel.updateUserData(name = name, email = email)
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = PurpleFont),
                                modifier = Modifier
                                    .padding(top = 16.dp, start = 24.dp, end = 24.dp)
                                    .fillMaxWidth()
                            )
                            {
                                Text("Save changes", fontSize = 18.sp)
                            }
                        }
                    }
        }
    }









@Preview
@Composable
private fun prev(){



}

