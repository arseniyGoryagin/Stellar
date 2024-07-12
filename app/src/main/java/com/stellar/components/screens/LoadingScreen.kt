package com.stellar.components.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.stellar.R
import com.stellar.screens.MyOrderScreen

@Composable
fun LoadingScreen(){

    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(id = R.drawable.loading),
        contentDescription =null )
}