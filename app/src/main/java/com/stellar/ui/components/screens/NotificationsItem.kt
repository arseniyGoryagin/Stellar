package com.stellar.ui.components.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stellar.ui.theme.Grey170

@Composable
fun NotificationItem(name : String, description : String, icon : @Composable () -> Unit, modifier: Modifier = Modifier){


    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
    ){
        icon()
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ){
            Text(name,
                fontSize = 20.sp,
                color = Color.Black
            )
            Text(text = description,
                color = Grey170,
                fontSize = 16.sp)
        }
    }


}



@Preview
@Composable
fun nnnn(){
    NotificationItem("Notification", "Some notification huhuhhuhhuhuhu", { Icon(
        Icons.Filled.Person,
        contentDescription = null
    )})
}