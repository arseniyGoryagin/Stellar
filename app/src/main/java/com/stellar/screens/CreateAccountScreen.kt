package com.stellar.screens

import android.renderscript.ScriptGroup.Input
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.stellar.components.Input.TextInput
import com.stellar.components.screens.ErrorScreen
import com.stellar.components.screens.LoadingScreen
import com.stellar.ui.theme.Grey170
import com.stellar.ui.theme.PurpleFont
import com.stellar.viewmodels.UserState
import com.stellar.viewmodels.UserViewModel
import org.jetbrains.annotations.Contract
import java.lang.Error

@Composable
fun CreateAccountScreen(navController: NavController, viewModel: UserViewModel){


    val userState = viewModel.userState



    /*LaunchedEffect(Unit) {
        viewModel.updateUserData()
    }*/



    when(userState){
        UserState.Error -> CreateAccountContent(viewModel = viewModel, "Error Creating an account")
        UserState.Loading -> LoadingScreen()
        UserState.Idle -> CreateAccountContent(viewModel = viewModel, null)
        is UserState.Success -> {navController.navigate("Home")}
    }




}


@Composable
fun CreateAccountContent(viewModel: UserViewModel, error: String? = null){



    var showPassword by remember {
        mutableStateOf(true)
    }

    var name by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }

    var error by remember {
        mutableStateOf(error)
    }



    Column(

        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text("Create Account",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier
                .padding(top = 20.dp, bottom = 8.dp, start = 16.dp, end= 16.dp))

        Text("Start shopping with create your account",
            fontSize = 14.sp ,
            color = Grey170,
            modifier = Modifier
                .padding( bottom = 8.dp, start = 16.dp, end= 16.dp))
        TextInput(name = "Name",
            placeholder = "Type in your name",
            icon = { Icon(
                Icons.Outlined.AccountCircle,
                contentDescription = null
            )},
            visibleText = true,
            trailingIcon = {},
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
            onValueChange = {name = it}
        )
        TextInput(name = "Email",
            placeholder = "Create your email",
            icon = { Icon(
                Icons.Outlined.Email,
                contentDescription = null
            )},
            visibleText = true,
            trailingIcon = {},
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
            onValueChange = {email= it})
        TextInput(name = "Password",
            placeholder = "Enter your password",
            icon = { Icon(
                Icons.Outlined.Lock,
                contentDescription = null
            )},
            visibleText = showPassword,
            trailingIcon = {
                IconButton(onClick = {showPassword = !showPassword}) {
                    Icon(Icons.Outlined.Info, contentDescription = "Show password")
                }
            },
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
            onValueChange = {password = it})


        Button(
            onClick = {
                println(name)
                viewModel.register(email, password, name)
            },
            colors = ButtonDefaults.buttonColors(containerColor = PurpleFont),
            modifier = Modifier
                .padding(top = 16.dp, start = 24.dp, end = 24.dp)
                .fillMaxWidth()
            )
        {
            Text("Create an Account", fontSize = 18.sp)
        }
        if (error != null){
            Text(
                text = error!!,
                color = Color.Red
            )
        }
    }

}



