package com.stellar.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.stellar.R
import com.stellar.components.Input.TextInput
import com.stellar.components.TopBars.ProfileTopBar
import com.stellar.data.User
import com.stellar.ui.theme.Grey170
import com.stellar.ui.theme.PurpleFont
import com.stellar.viewmodels.MyProfileViewModel
import com.stellar.viewmodels.SaveDataState
import com.stellar.viewmodels.UserState
import com.stellar.viewmodels.UserViewModel


@Composable
fun MyProfileScreen(navController: NavController, userViewModel: UserViewModel, myProfileViewModel: MyProfileViewModel){


    val savingState = myProfileViewModel.saveDataState
    val userState = userViewModel.userState

    if (userState is UserState.Idle || userState is UserState.Error){
        navController.navigate("Welcome")
    }

    LaunchedEffect(Unit) {
        myProfileViewModel.resetState()
    }


    var onSaveChanged = { name : String, email : String ->
        myProfileViewModel.updateUserData(name, email)
    }

    var onNewValue = {
        myProfileViewModel.resetState()
    }

    var goToWelcomePage = {
        navController.navigate("Welcome")
    }



    Scaffold(
        topBar = { ProfileTopBar(onBackClick = {navController.navigateUp()}, onNotificationClick = {navController.navigate("Notifications")}) },
        content = { innerPadding ->
                when(userState){
                    is UserState.Error -> {goToWelcomePage()}
                    UserState.Idle -> {userViewModel.updateUserData()}
                    UserState.Loading -> { CircularProgressIndicator()}
                    is UserState.Success -> {
                        MyProfileContent(
                            onSaveChanges = onSaveChanged,
                            onNewValue = onNewValue,
                            savingState = savingState,
                            userData = userState.userData,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
        }
    )

}








@Composable
private fun MyProfileContent(userData: User,
                             savingState : SaveDataState,
                             onSaveChanges : (String, String) -> Unit,
                             onNewValue : () -> Unit,
                             modifier: Modifier = Modifier){




    /*
    if(success != null){
        LaunchedEffect(Unit) {
            userViewModel.updateUserData()
        }
    }*/

        val context = LocalContext.current

        var isFirstChange by remember {
            mutableStateOf(false)
        }


        var name by remember {
            mutableStateOf(userData.name)
        }

        var email by remember {
            mutableStateOf(userData.email)
        }



        var onEmailValueChange = { newEmail : String ->
            email = newEmail
        }


        var onNameValueChange = { newName : String ->
            name = newName
        }

        var onSaveChangesClick = { name : String, email : String ->
            isFirstChange = true
            onSaveChanges(name, email)
        }


        LaunchedEffect(key1 = name, email) {
            if(isFirstChange == true){
                isFirstChange = false
                onNewValue()
            }
        }




        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
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
                modifier = Modifier.clip(CircleShape),
                error = {
                    Image(
                        painter = painterResource(id = R.drawable.nopic),
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


            val enabled = if(savingState is SaveDataState.Loading){false}else{true}
            val error = if(savingState is SaveDataState.Error && isFirstChange == true){true}else{false}

            TextInput(
                    error =error,
                    enabled = enabled,
                    name = "Name",
                    startValue = userData.name,
                    modifier = Modifier
                    .fillMaxWidth(),
                    icon = {
                    Icon(Icons.Filled.Person, contentDescription = null)
                    },
                    trailingIcon = { /*TODO*/ },
                    onValueChange = onNameValueChange,
                    visibleText = true
            )
            TextInput(
                    error = error,
                    enabled = enabled,
                    name = "Email",
                    startValue = userData.email,
                    modifier = Modifier
                        .fillMaxWidth(),
                    icon = {
                        Icon(Icons.Filled.MailOutline, contentDescription = null)
                    },
                    trailingIcon = {
                    },
                    onValueChange = onEmailValueChange,
                    visibleText = true
            )


            val buttonColor = if(savingState is SaveDataState.Success){
                Grey170
            }else{
                PurpleFont
            }


            Button(
                onClick = if(savingState is SaveDataState.Idle || savingState is SaveDataState.Error)
                {{onSaveChangesClick(name, email)}}
                else{{}},
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                modifier = Modifier
                    .padding(top = 16.dp, start = 24.dp, end = 24.dp)
                    .fillMaxWidth()
            )
            {

                when(savingState){
                    is SaveDataState.Error -> {
                        Text(
                            "Save changes",
                            fontSize = 18.sp,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                    }
                    SaveDataState.Idle -> { Text(
                        "Save changes",
                        fontSize = 18.sp,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )}
                    SaveDataState.Loading -> {CircularProgressIndicator()}
                    SaveDataState.Success -> {
                        Text(
                            "Saved changes",
                            fontSize = 18.sp,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                    }
                }

            }

            if(savingState is SaveDataState.Error){
                val message : String? = when(savingState.e){
                    is retrofit2.HttpException ->{
                        val repsonseBody = savingState.e.response()?.errorBody()?.string()
                        repsonseBody
                    }
                    else -> savingState.e.localizedMessage
                }

                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }

            if(savingState is SaveDataState.Success){
                Toast.makeText(context, "Profile saved", Toast.LENGTH_SHORT).show()
            }



        }




}









@Preview
@Composable
private fun prev(){



}

