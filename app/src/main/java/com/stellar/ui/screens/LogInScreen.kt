package com.stellar.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.stellar.R
import com.stellar.ui.theme.Grey170
import com.stellar.ui.theme.PurpleFont
import com.stellar.components.Input.TextInput
import com.stellar.viewmodels.LoginState
import com.stellar.viewmodels.LoginViewModel

@Composable
fun LogInScreen(navController: NavController, viewmodel : LoginViewModel){

    val loginState = viewmodel.loginState


    var onLogin = { email : String, password : String ->
        viewmodel.login(email, password)
    }

    LaunchedEffect(key1 = loginState) {
        if(loginState is LoginState.Success){
            viewmodel.resetState()
            navController.navigate("Home")
        }
    }

    LogInContent(loginState = loginState, onLogin = onLogin)

}


@Composable
fun LogInContent(loginState : LoginState, onLogin : (String, String) -> Unit){

    val context = LocalContext.current


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

    var inputChanged by remember {
        mutableStateOf(false)
    }

    var errorToastShown by remember {
        mutableStateOf(false)
    }

    var onLoginClick = { email : String, password : String ->
        onLogin(email, password)
        inputChanged = false
        errorToastShown = false
    }


    LaunchedEffect(key1 = password, email) {
        if (inputChanged == false){
            inputChanged = true
        }
    }


    val enabled  = if(loginState is LoginState.Loading){false}else{true}
    val error = if(loginState is LoginState.Error && inputChanged == false){true}else{false}

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
            error = error,
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




        TextInput(
            enabled = enabled,
            error = error,
            name = "Password",
            placeholder = "Enter your password",
            icon = { Icon(
                Icons.Outlined.Lock,
                contentDescription = "Password"
            )},
            visibleText = showPassword,
            trailingIcon = {

                val icon = if(!showPassword){
                    R.drawable.eye_off_outline}else{
                    R.drawable.eye_outline}

                IconButton(onClick = {showPassword = !showPassword}) {
                    Icon(painter = painterResource(id = icon), contentDescription = "Show password")
                }
            },
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
            onValueChange = onPasswordValueChaned)


        Button(
            onClick = if(loginState is LoginState.Idle || loginState is LoginState.Error)
            {{onLoginClick(email, password)}}
            else{{}},
            colors = ButtonDefaults.buttonColors(containerColor = PurpleFont),
            modifier = Modifier
                .padding(top = 16.dp, start = 24.dp, end = 24.dp)
                .fillMaxWidth()
        )
        {
            if(loginState is LoginState.Loading)
                CircularProgressIndicator(color = Color.White,)
            else
                Text("Log in",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
        }


        if(loginState is LoginState.Error && errorToastShown== false){
            val message : String? = when(loginState.e){
                is retrofit2.HttpException ->{
                    when(loginState.e.code()){
                        401 ->{ "Wrong Email or Password"}
                        else ->{loginState.e.response()?.errorBody()?.string()}
                    }
                }
                else -> loginState.e.localizedMessage
            }
            errorToastShown = true
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }


    }

}


