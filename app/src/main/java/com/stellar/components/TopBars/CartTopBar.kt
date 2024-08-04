package com.stellar.components.TopBars

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.stellar.R
import com.stellar.components.Buttons.BackButton


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartTopBar(onBackClick : () -> Unit, modifier: Modifier = Modifier){
    CenterAlignedTopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        title= {
            Text(text = "My Cart")
        },
        navigationIcon = {
            BackButton(onClick = onBackClick)
        },
    )



}