package com.stellar.components.Input

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stellar.ui.theme.Grey170
import com.stellar.ui.theme.PurpleFont

@Composable
fun TextInput(name : String, placeholder :String, modifier: Modifier, icon : @Composable () -> Unit, trailingIcon : @Composable () -> Unit, onValueChange : (String) -> Unit, visibleText : Boolean){

    var inputValue by remember {
        mutableStateOf("")
    }


    Column(modifier = modifier) {
        Text(name, fontSize = 16.sp)
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
            onValueChange = {
                inputValue = it
                onValueChange(it)
                            },
            shape = RoundedCornerShape(20.dp),
            leadingIcon = icon,
            trailingIcon = trailingIcon,
            visualTransformation = if(visibleText) VisualTransformation.None else PasswordVisualTransformation()

        )
    }

}