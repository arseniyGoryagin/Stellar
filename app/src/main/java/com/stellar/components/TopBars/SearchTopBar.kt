package com.stellar.components.TopBars

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.stellar.R
import com.stellar.components.Buttons.BackButton
import com.stellar.components.Buttons.NotificationButton
import com.stellar.ui.theme.Grey170
import com.stellar.ui.theme.PurpleFont
import com.stellar.viewmodels.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchToopBar(navController : NavController, viewModel: SearchViewModel = hiltViewModel(), onFilter : () -> Unit){

    val currentSearch = viewModel.currentSearch

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        ),
        title= {
            SearchInput(
                placeholder = "Cheap Bags",
                onFilter = onFilter,
                value = currentSearch,
                onValueChanged =
                { search ->
                    navController.navigate("Search/${search}")
                },
                onFocusChanged = {it
                    viewModel.changeSearchActive(it.isFocused)
                },
                onSearch = { search ->
                    navController.navigate("Search/${search}")
                    println("Searching --- \n" + search)
                    viewModel.saveSearch(search)
                }

                )
        },
        navigationIcon = {
            BackButton(navController = navController)
            viewModel.getProducts("")
        },
        actions={
            NotificationButton(navController)
        }
    )}



@Composable
fun SearchInput(placeholder :String, value : String, onValueChanged : (search : String) -> Unit, onFilter: () -> Unit, onFocusChanged : (FocusState) -> Unit, onSearch : (String) -> Unit){

    var inputValue by remember {
        mutableStateOf(value)
    }


        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(63.dp)
                .onFocusChanged { focuseState ->
                    onFocusChanged(focuseState)
                },
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
                onValueChanged(it)
                            },
            shape = RoundedCornerShape(20.dp),
            leadingIcon = {
                IconButton(onClick = { onSearch(inputValue)}) {
                    Icon(Icons.Outlined.Search, contentDescription = "Search")
                }
            },
            trailingIcon ={

                IconButton(onClick = { onFilter()}) {
                    Icon(painter = painterResource(id = R.drawable.tune), contentDescription = null)
                }
            }
        )


}


