package com.stellar.components.Input

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


@Composable
fun SearchBar(onSearch : (String) -> Unit){
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
    }

}