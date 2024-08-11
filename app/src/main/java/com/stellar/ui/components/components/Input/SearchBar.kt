package com.stellar.components.Input

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.stellar.ui.theme.Grey170
import com.stellar.ui.theme.PurpleFont


@Composable
fun SearchBar(onSearch : (String) -> Unit, iconRight : Unit? = null){
    var input by remember { mutableStateOf("") }

    Row {
        IconButton(
            onClick={
                onSearch(input)
            }) {
            Icon(Icons.Filled.Search, contentDescription = "Search", )
        }

        TextField(value = input, onValueChange = {
            input = it
        })
        iconRight
    }

}



