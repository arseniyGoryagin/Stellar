package com.stellar.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.room.util.newStringBuilder
import com.stellar.R
import com.stellar.ui.theme.Grey170
import com.stellar.ui.theme.PurpleFont
import com.stellar.components.Input.TextInput
import com.stellar.viewmodels.LoginState
import com.stellar.viewmodels.LoginViewModel
import com.stellar.viewmodels.RegisterState

@Composable
fun LogInScreen(navController: NavController, viewmodel : LoginViewModel){



    val loginState = viewmodel.loginState


    var onLogin = {
        navController.navigate("Home")
    }


    LogInContent(loginState = loginState, onLogin = onLogin)

}


@Composable
fun LogInContent(loginState : LoginState, onLogin : () -> Unit){

    var showPassword by remember {
        mutableStateOf(true)
    }

    var password by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }

    var onEmailValueChanegd = { newEmail : String ->
        email = newEmail
    }

    var onPasswordValueChaned = { newPassword: String ->
        password = newPassword
    }


    val enabled  = if(loginState is LoginState.Loading){false}else{true}

    Column() {
        Text("Log in",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier
                .padding(top = 20.dp, bottom = 8.dp, start = 16.dp, end= 16.dp))

        Text("Please login with registered account",
            fontSize = 14.sp ,
            color = Grey170,
            modifier = Modifier
                .padding( bottom = 8.dp, start = 16.dp, end= 16.dp))


        TextInput(
            enabled = enabled,
            name = "Email",
            placeholder = "Enter your email",
            icon = { Icon(
                Icons.Outlined.Email,
                contentDescription = "Email"
            )},
            visibleText = true,
            trailingIcon = {},
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
            onValueChange = onEmailValueChanegd)


        val icon = if(!showPassword){
            R.drawable.eye_off_outline}else{
            R.drawable.eye_outline}

        TextInput(name = "Password",
            placeholder = "Enter your password",
            icon = { Icon(
                Icons.Outlined.Lock,
                contentDescription = "Password"
            )},
            visibleText = showPassword,
            trailingIcon = {
                IconButton(onClick = {showPassword = !showPassword}) {
                    Icon(painter = painterResource(id = icon), contentDescription = "Show password")
                }
            },
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
            onValueChange = onPasswordValueChaned)


        Button(
            onClick = if(loginState is LoginState.Idle || loginState is LoginState.Error){onLogin}else{{}},
            colors = ButtonDefaults.buttonColors(containerColor = PurpleFont),
            modifier = Modifier
                .padding(top = 16.dp, start = 24.dp, end = 24.dp)
                .fillMaxWidth()
        )
        {
            if(loginState is LoginState.Loading)
                CircularProgressIndicator()
            else
                Text("Create an Account",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
        }
    }

}


