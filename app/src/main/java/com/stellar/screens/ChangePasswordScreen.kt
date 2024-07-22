package com.stellar.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.navArgument
import com.stellar.components.Input.TextInput
import com.stellar.R
import com.stellar.components.TopBars.GenericTopBar
import com.stellar.components.screens.LoadingScreen
import com.stellar.ui.theme.PurpleFont
import com.stellar.viewmodels.ChangePasswordState
import com.stellar.viewmodels.ChangePasswordViewModel

@Composable
fun ChangePasswordScreen(navController: NavController, viewModel: ChangePasswordViewModel){
    val changePasswordState = viewModel.changePasswordState
    
    
    LaunchedEffect(key1 = Unit) {
        viewModel.resetState()
    }

    when(changePasswordState){
        ChangePasswordState.Error -> ChangePasswordContent(navController= navController, viewModel = viewModel, "Error changing password")
        ChangePasswordState.Idle -> ChangePasswordContent(viewModel = viewModel, navController = navController)
        ChangePasswordState.Loading -> LoadingScreen()
        ChangePasswordState.Success -> ChangePasswordContent(viewModel = viewModel, navController = navController, success = "Password changed successfully")
    }


}


@Composable
fun ChangePasswordContent(navController: NavController,  viewModel: ChangePasswordViewModel, error: String = "", success : String = ""){

    val changePasswordState = viewModel.changePasswordState


    var showNewPassword by remember{
        mutableStateOf(false)
    }
    var showConfirmPassword by remember{
        mutableStateOf(false)
    }


    var newPassword by remember{
        mutableStateOf("")
    }
    var confirmPassword by remember{
        mutableStateOf("")
    }

    var error by remember {
        mutableStateOf(error)
    }

    var success by remember {
        mutableStateOf(success)
    }



    Scaffold(

        topBar = { GenericTopBar(navController = navController, name = "Password")},
        content = { innerPadding ->


            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.SpaceBetween,


                ){
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    TextInput(
                        name = "New Password",
                        icon = { Icon(Icons.Filled.Lock, contentDescription = null)},
                        trailingIcon = { IconButton(onClick = { showNewPassword = !showNewPassword }) {
                            Icon(painterResource(id = R.drawable.eye_outline), null)
                        }},
                        onValueChange = {newPassword = it},
                        visibleText = showNewPassword,
                        placeholder = "Enter new password"
                    )
                    TextInput(
                        name = "Confirm Password",
                        icon = { Icon(Icons.Filled.Lock, contentDescription = null)},
                        trailingIcon = { IconButton(onClick = { showConfirmPassword = !showConfirmPassword }) {
                            Icon(painterResource(id = R.drawable.eye_outline), null)
                        }},
                        onValueChange = {confirmPassword = it},
                        visibleText = showConfirmPassword,
                        placeholder = "Confirm password"
                    )

                    if(success != ""){
                        Text(
                            success,
                            color = Color.Green
                        )
                    }
                    if(error != ""){
                        Text(
                            error,
                            color = Color.Red
                        )
                    }

                }


                Button(
                    onClick = {
                        if(newPassword != confirmPassword){
                            error = "Password do not match"
                        }
                        else {
                            viewModel.changePassword(newPassword)
                            error = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PurpleFont),
                    modifier = Modifier
                        .padding(top = 16.dp, start = 24.dp, end = 24.dp)
                        .fillMaxWidth()
                )
                {
                    Text("Change Now", fontSize = 18.sp)
                }


            }


        }
    )





}


