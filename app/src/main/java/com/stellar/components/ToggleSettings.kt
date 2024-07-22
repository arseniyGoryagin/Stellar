package com.stellar.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ToggleSetting(name : String, startToggle : Boolean, onToggleChange : (Boolean) -> Unit, modifier: Modifier = Modifier){


    var toggleValue by remember {
        mutableStateOf(startToggle)
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically

    ){
        Text(text = name)
        Switch(checked = toggleValue, onCheckedChange = {
            onToggleChange(it)
            toggleValue = it
        })
    }



}