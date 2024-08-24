package com.stellar.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.stellar.components.Input.TextInput
import com.stellar.ui.theme.Grey170
import com.stellar.ui.theme.PurpleFont
import com.stellar.R
import com.stellar.data.types.Notification
import com.stellar.viewmodels.CreateAccountViewModel
import com.stellar.viewmodels.RegisterState
import kotlinx.coroutines.launch

@Composable
fun CreateAccountScreen(navController: NavController, viewModel: CreateAccountViewModel){


    val registerState = viewModel.registerState

    val context = LocalContext.current


    val scope = rememberCoroutineScope()


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

    var inputChanged by remember {
        mutableStateOf(false)
    }
    var errorToastShown by remember {
        mutableStateOf(true)
    }

    //

    var onCreateAccountButtonClick = {
        viewModel.register(email, password, name)
        inputChanged = false
        errorToastShown = false
    }

    var onDismissBottomSheet = {
        navController.navigate("Log in")
        scope.launch {
            viewModel.resetState()
        }
        Unit
    }

    LaunchedEffect(key1 = email, password, name) {
        if(inputChanged == false){
            inputChanged = true
        }

    }

    val enabled  = if(registerState is RegisterState.Loading){false}else{true}
    val error = if(registerState is RegisterState.Error && inputChanged == false){true}else{false}

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
        TextInput(
            enabled = enabled,
            name = "Name",
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
        TextInput(
            error = error,
            enabled = enabled,
            name = "Email",
            placeholder = "Create your email",
            icon = { Icon(
                Icons.Outlined.Email,
                contentDescription = null
            )},
            visibleText = true,
            trailingIcon = {},
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
            onValueChange = {email= it})
        TextInput(
            error = error,
            enabled = enabled,
            name = "Password",
            placeholder = "Enter your password",
            icon = { Icon(
                Icons.Outlined.Lock,
                contentDescription = null
            )},
            visibleText = showPassword,
            trailingIcon = {
                val icon = if(!showPassword){R.drawable.eye_off_outline}else{R.drawable.eye_outline}
                IconButton(onClick = {showPassword = !showPassword}) {
                    Icon(painter = painterResource(id = icon), contentDescription = "Show password")
                }
            },
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
            onValueChange = {password = it})


        Button(
            onClick = if(registerState is RegisterState.Idle || registerState is RegisterState.Error)
                        {onCreateAccountButtonClick }
                        else{ {} },
            colors = ButtonDefaults.buttonColors(containerColor = PurpleFont),
            modifier = Modifier
                .padding(top = 16.dp, start = 24.dp, end = 24.dp)
                .fillMaxWidth()
        )
        {
            if(registerState is RegisterState.Loading)
                CircularProgressIndicator(
                    color = Color.White
                )
            else
                Text("Create an Account",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 10.dp)
                    )
        }


        if(registerState is RegisterState.Success){
            val welcomeNotifName = context.getString(R.string.WelcomeNotificationName)
            val welcomeNotifDescription = context.getString(R.string.WelcomeNotificationDescription)
            viewModel.sendNotification(
                name = welcomeNotifName,
                description = welcomeNotifDescription,
                icon = Notification.SALE_ICON
            )
            RegisterSuccessBottomSheet(onDismiss = onDismissBottomSheet)
        }

        if(registerState is RegisterState.Error && errorToastShown== false){
            val message : String? = when(registerState.e){
                is retrofit2.HttpException ->{
                    val repsonseBody = registerState.e.response()?.errorBody()?.string()
                    println("Repsonce  === " + repsonseBody)
                    repsonseBody
                }
                else -> registerState.e.localizedMessage
            }
            errorToastShown = true
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterSuccessBottomSheet(onDismiss : () -> Unit){


    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()


    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color.White,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                painter = painterResource(id = R.drawable.successtick),
                contentDescription = null,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .height(200.dp)
                    .width(200.dp)
                    .clip(
                        CircleShape
                    ))
            Text(
                text = "Register Success",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
            )
            Text(
                textAlign = TextAlign.Center,
                text = "Congratulations! Your account already created",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Grey170,
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
            )
            Button(
                onClick = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        onDismiss()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = PurpleFont),
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp, start = 24.dp, end = 24.dp)
                    .fillMaxWidth()
            )
            {
                Text(
                    text ="Log in",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 10.dp))
            }
        }
    }

}



