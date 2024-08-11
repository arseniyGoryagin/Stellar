package com.stellar.components.TopBars

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.stellar.components.Buttons.BackButton
import com.stellar.components.Buttons.MapButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenericTopBar(onBackClick : () -> Unit,  name : String){


        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White
            ),
            title= {
                Text(text = name)
            },
            navigationIcon = {
                BackButton(onClick = onBackClick)
            },
            actions={
              //  Icon(Icons.Filled.MoreVert, contentDescription = null)
            }
        )




}