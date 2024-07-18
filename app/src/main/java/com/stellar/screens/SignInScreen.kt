package com.stellar.screens

import android.renderscript.ScriptGroup.Input
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.stellar.ui.theme.Grey170
import com.stellar.ui.theme.PurpleFont
import org.jetbrains.annotations.Contract
import com.stellar.components.Input.TextInput

@Composable
fun SignInScreen(navController: NavController){

    var showPassword by remember {
        mutableStateOf(true)
    }


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




        TextInput(name = "Email",
            placeholder = "Enter your email",
            icon = { Icon(
                Icons.Outlined.Email,
                contentDescription = "Email"
            )},
            visibleText = true,
            trailingIcon = {},
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
            onValueChange = {})

        TextInput(name = "Password",
            placeholder = "Enter your password",
            icon = { Icon(
                Icons.Outlined.Lock,
                contentDescription = "Password"
            )},
            visibleText = showPassword,
            trailingIcon = {

                IconButton(onClick = {showPassword = !showPassword}) {
                    Icon(Icons.Outlined.Info, contentDescription = "Show password")
                }



            },
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
            onValueChange = {})


        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = PurpleFont),
            modifier = Modifier
                .padding(top = 16.dp, start = 24.dp, end = 24.dp)
                .fillMaxWidth()
        )
        {
            Text("Sign in", fontSize = 18.sp)
        }
    }
}


