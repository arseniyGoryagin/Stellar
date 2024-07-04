package com.stellar.components.TopBars

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.stellar.components.Buttons.BackButton
import com.stellar.components.Buttons.NotificationButton
import com.stellar.ui.theme.Grey170
import com.stellar.ui.theme.PurpleFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(navController : NavController){



    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        ),
        title= {
            SearchInput(placeholder = "Cheap Bags")
        },
        navigationIcon = {
            BackButton(navController = navController)
        },
        actions={
            NotificationButton(navController)
        }
    )}



@Composable
fun SearchInput(placeholder :String){

    var inputValue by remember {
        mutableStateOf("")
    }


        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = inputValue,
            placeholder = { Text(placeholder) },
            label = {},
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(245, 245, 245),
                unfocusedContainerColor = Color(245, 245, 245),
                focusedPlaceholderColor = Grey170,
                unfocusedPlaceholderColor = Grey170,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = PurpleFont,
                focusedLeadingIconColor = if(inputValue.length > 0) PurpleFont else Grey170,
                unfocusedLeadingIconColor = if(inputValue.length > 0) PurpleFont else Grey170,
            ),
            onValueChange = {inputValue = it},
            shape = RoundedCornerShape(20.dp),
            leadingIcon = {Icon(Icons.Outlined.Search, contentDescription = "Search")},
        )


}


