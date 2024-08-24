package com.stellar.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.stellar.components.Input.TextInput
import com.stellar.components.TopBars.GenericTopBar
import com.stellar.R
import com.stellar.ui.theme.PurpleFont
import com.stellar.viewmodels.AddNewCardViewModel
import com.stellar.viewmodels.AddingNewCardState
import com.stellar.viewmodels.CardError
import com.stellar.viewmodels.CardErrors
import kotlinx.coroutines.launch
import kotlinx.coroutines.processNextEventInCurrentThread

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddNewCardScreen(navController: NavController, viewModel: AddNewCardViewModel){


    val addNewCardState = viewModel.newCardState

    val addNewCardCb =
        { cardNumber : String, holdersName : String, cvvCode : String, date : String ->
            viewModel.addNewCard(name = holdersName, number = cardNumber, cvv = cvvCode, date = date)
        }


    var onDismissBottomSheet = {
        navController.navigate("Payment")
    }


    Scaffold(
        topBar = { GenericTopBar(onBackClick = {navController.navigateUp()}, name = "Add New Card")},
        content = { padding ->
            AddCardContent(
                onAddNewCard = addNewCardCb,
                addNewCardState = addNewCardState,
                modifier = Modifier.padding(padding)
            )
            if(addNewCardState is AddingNewCardState.Success)
            CardAddedSheet(
                onDismiss = onDismissBottomSheet
            )
        }
    )


}


@Composable
fun AddCardContent(
    onAddNewCard : (String, String, String, String) -> Unit,
                   addNewCardState : AddingNewCardState,
                    modifier: Modifier = Modifier)
{


    var scroll = rememberScrollState()

    var cardNumber by remember{
        mutableStateOf("")
    }
    var holdersName by remember{
        mutableStateOf("")
    }

    var expired by remember{
        mutableStateOf("")
    }

    var cvvCode by remember{
        mutableStateOf("")
    }


    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .padding(top = 20.dp)
            .verticalScroll(scroll),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var errors : CardErrors? = null
        if(addNewCardState is AddingNewCardState.Errors){
            errors = addNewCardState.errors
        }



        TextInputWithErrorMessage(
            name = "Card Number",
            icon = { Icon(Icons.Filled.Menu, contentDescription = null) },
            trailingIcon = { },
            onValueChange = {
                cardNumber = it
            } ,
            visibleText = true,
            placeholder = "Enter Card Number",
            error = errors?.numberError
        )




        TextInputWithErrorMessage(
            name = "Card Holders Name",
            icon = { Icon(Icons.Filled.Person, contentDescription = null) },
            trailingIcon = { /*TODO*/ },
            onValueChange = {
                holdersName = it
            } ,
            visibleText = true,
            placeholder = "Enter Holders Name",
            error =  errors?.nameError
        )


        TextInputWithErrorMessage(
            name = "Expired",
            icon = { Icon(painter = painterResource(id = R.drawable.calendar_multiselect), contentDescription = null) },
            trailingIcon = {},
            onValueChange = {
                expired = it
            } ,
            visibleText = true,
            placeholder = "MM/YY",
            error = errors?.dateError
        )



        TextInputWithErrorMessage(
            name = "CVV Code",
            icon = { Icon(Icons.Filled.Lock, contentDescription = null) },
            trailingIcon = {},
            onValueChange = {
                cvvCode = it
            },
            visibleText = true,
            placeholder = "CVV",
            error =errors?.cvvError
        )



        Button(
            onClick = {
                onAddNewCard(cardNumber, holdersName, cvvCode, expired)
            },
            colors = ButtonDefaults.buttonColors(containerColor = PurpleFont),
            modifier = Modifier
                .padding(top = 16.dp, bottom = 16.dp, start = 24.dp, end = 24.dp)
                .fillMaxWidth()
        )
        {
            if(addNewCardState is AddingNewCardState.Loading){
                CircularProgressIndicator(
                    color = Color.White
                )
            }
            Text("Add Card",
                fontSize = 18.sp,
                modifier = Modifier.padding(vertical = 10.dp))
        }



        if(addNewCardState is AddingNewCardState.Errors && addNewCardState.errors.otherError != null){
            val error = addNewCardState.errors.otherError
            if (error != null) {
                Text(
                    text = error,
                    color = Color.Red)
            }
        }
    }

}


@Composable
fun TextInputWithErrorMessage(name: String,
                              startValue: String = "",
                              placeholder: String = "",
                              icon: @Composable () -> Unit,
                              trailingIcon: @Composable () -> Unit,
                              onValueChange: (String) -> Unit,
                              visibleText: Boolean,
                              error: String? = null,
                              enabled: Boolean = true,
                              modifier: Modifier = Modifier){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        TextInput(
            name = name,
            startValue = startValue,
            placeholder = placeholder,
            icon = icon,
            trailingIcon = trailingIcon,
            onValueChange = onValueChange,
            visibleText = visibleText,
            error = error != null,
            enabled = enabled,
            modifier = Modifier.fillMaxWidth()
            )
        if (error != null) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = error,
                color = Color.Red,
                textAlign = TextAlign.Center,
                fontSize = 16.sp
                )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardAddedSheet(onDismiss : () -> Unit){

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()


    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color.White,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                painter = painterResource(id = R.drawable.successtick),
                contentDescription = null,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .height(200.dp)
                    .width(200.dp)
                    .clip(CircleShape))
            Text(
                text = "Card Add successfully",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
            )
            Button(
                onClick = {
                    scope.launch { sheetState.hide() }
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(containerColor = PurpleFont),
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp, start = 24.dp, end = 24.dp)
                    .fillMaxWidth()
            )
            {
                Text("Back to Payment",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 10.dp))
            }
        }
    }


}