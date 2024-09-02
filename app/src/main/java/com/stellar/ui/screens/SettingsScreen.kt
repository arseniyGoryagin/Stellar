package com.stellar.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.stellar.components.TopBars.SettingsTopBar
import com.stellar.R
import com.stellar.ui.theme.Grey241
import com.stellar.ui.theme.PurpleFont
import com.stellar.viewmodels.SettingsViewModel


@Composable
fun SettingsScreen(navController : NavController, viewmodel : SettingsViewModel){


    var showAlertDialog by remember{
        mutableStateOf(false)
    }

    val onLogOutButtonClick = {
        showAlertDialog = true
    }

    val onLogOutConfirm = {
        showAlertDialog = false
        viewmodel.logOut()
        navController.navigate("Welcome")
    }

    val onDismissButton = {
        showAlertDialog = false
    }


    Scaffold(
        topBar = {
            SettingsTopBar(onBackClick = {navController.navigateUp()})

                 },
        content = { innerPadding ->

                var scroll = rememberScrollState()

                Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = innerPadding.calculateTopPadding(), bottom = 0.dp)
                            .background(Grey241)
                            .padding(horizontal = 16.dp)
                            .padding(top = 16.dp)
                            .verticalScroll(scroll)

                ) {
                    Text(
                        text = "General",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(5.dp)
                    )
                    SettingsItem(modifier = Modifier.fillMaxWidth(), value = "Edit Profile", icon = {Icon(Icons.Filled.Person, contentDescription = null)},
                        onClick = {navController.navigate("My Profile")})
                    SettingsItem(modifier = Modifier.fillMaxWidth(),value = "Change password", icon = {Icon(Icons.Filled.Lock, contentDescription = null)},
                        onClick = {navController.navigate("Change Password")})
                    SettingsItem(modifier = Modifier.fillMaxWidth(),value = "Notificafions", icon = {
                        Icon(painterResource(id = R.drawable.bell_outline), contentDescription = null)},
                        onClick = {navController.navigate("Notifications Settings")})
                    SettingsItem(modifier = Modifier.fillMaxWidth(),value = "Security",
                        icon = {Icon(painterResource(id = R.drawable.shield_lock_outline), contentDescription = null)},
                        onClick = {navController.navigate("Security Settings")})
                    SettingsItem(modifier = Modifier.fillMaxWidth(),value = "Language",
                        icon = {Icon(painterResource(id = R.drawable.web), contentDescription = null)},
                        onClick = {navController.navigate("Language Settings")})
                    Text(
                        text = "Preferences",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(5.dp)
                    )
                    SettingsItem(modifier = Modifier.fillMaxWidth(),value = "Legal and Polices",
                        icon = {Icon(painterResource(id = R.drawable.shield_outline), contentDescription = null)}, onClick = {
                            navController.navigate("Legal and Polices")
                        })
                    SettingsItem(modifier = Modifier.fillMaxWidth(),value = "Help & Support",
                        icon = {Icon(painterResource(id = R.drawable.comment_question_outline), contentDescription = null)},
                        onClick = { navController.navigate("Help and Support")})
                    LogOutItem(modifier = Modifier.fillMaxWidth(),value = "LogOut",
                        icon = {Icon(
                            painterResource(id = R.drawable.logout),
                            contentDescription = null,
                            tint = Color.Red
                                )
                               },
                        onClick = onLogOutButtonClick)


                    
                    if(showAlertDialog){
                        LogOutDialog(
                            onDismiss = onDismissButton,
                            onLogOut = onLogOutConfirm,
                            onCancel = onDismissButton
                        )
                    }
                }
        },

    )




}


@Composable
fun LogOutDialog(onCancel: () -> Unit, onLogOut : () -> Unit, onDismiss : () -> Unit){
    AlertDialog(
        title = {
            Text(
                "Are you sure you want to log out?",
                textAlign = TextAlign.Center
            )
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = onLogOut,
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                modifier = Modifier
                    .padding(top = 16.dp, start = 24.dp, end = 24.dp)
                    .fillMaxWidth()
            )
            {
                Text("Logout",
                    fontSize = 18.sp,
                    color = Color.Red
                )
            }
        },
        dismissButton = {
            Button(
                onClick = onCancel,
                colors = ButtonDefaults.buttonColors(containerColor = PurpleFont),
                modifier = Modifier
                    .padding(top = 16.dp, start = 24.dp, end = 24.dp)
                    .fillMaxWidth()
            )
            {
                Text(
                    "Cancel",
                    fontSize = 18.sp,
                    color = Color.White
                )
            }
        },
        containerColor = Color.White
    )

}


@Composable
fun SettingsItem(value : String, onClick : () -> Unit,  icon : @Composable () -> Unit, modifier: Modifier = Modifier ){


        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .clickable {
                    onClick()
                }
                .padding(start = 16.dp)
        ) {
            Row(
                modifier = Modifier

            ) {
                icon()
                Text(
                    text = value,
                    modifier = Modifier.padding(start = 16.dp),
                    fontSize = 16.sp
                )
            }
            Icon(
                    painter = painterResource(id = R.drawable.chevron_right),
                    contentDescription = null
            )

        }
}


@Composable
fun LogOutItem(value : String, onClick : () -> Unit,  icon : @Composable () -> Unit, modifier: Modifier = Modifier ){


    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .clickable {
                onClick()
            }
            .padding(start = 16.dp)
    ) {
        Row(
            modifier = Modifier

        ) {
            icon()
            Text(
                text = value,
                modifier = Modifier.padding(start = 16.dp),
                fontSize = 16.sp
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.chevron_right),
            contentDescription = null
        )

    }
}
