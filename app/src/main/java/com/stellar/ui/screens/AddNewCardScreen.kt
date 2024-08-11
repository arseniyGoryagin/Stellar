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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.stellar.components.Input.TextInput
import com.stellar.components.TopBars.GenericTopBar
import com.stellar.R
import com.stellar.ui.theme.PurpleFont
import com.stellar.viewmodels.AddNewCardViewModel
import com.stellar.viewmodels.AddingNewCardState
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddNewCardScreen(navController: NavController, viewModel: AddNewCardViewModel){


    val addNewCardState = viewModel.newCardState

    val addNewCardCb =
        { cardNumber : String, holdersName : String, cvvCode : String, date : String ->
            viewModel.addNewCard(name = holdersName, number = cardNumber, cvv = cvvCode.toInt(), date = date)
        }


    var onDismissBottomSheet = {
        navController.navigate("Payment")
        viewModel.resetState()
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
                    modifier: Modifier = Modifier){

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
            .padding(top = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TextInput(
            name = "Card Number",
            icon = { Icon(Icons.Filled.Menu, contentDescription = null) },
            trailingIcon = { /*TODO*/ },
            onValueChange = {
                cardNumber = it
            } ,
            visibleText = true,
            placeholder = "Enter Card Number"
        )
        TextInput(
            name = "Card Holders Name",
            icon = { Icon(Icons.Filled.Person, contentDescription = null) },
            trailingIcon = { /*TODO*/ },
            onValueChange = {
                holdersName = it
            } ,
            visibleText = true,
            placeholder = "Enter Holders Name"
        )
        TextInput(
            name = "Expired",
            icon = { Icon(painter = painterResource(id = R.drawable.calendar_multiselect), contentDescription = null) },
            trailingIcon = { /*TODO*/ },
            onValueChange = {
                expired = it
            } ,
            visibleText = true,
            placeholder = "MM/YY"
        )
        TextInput(
            name = "CVV Code",
            icon = { Icon(Icons.Filled.Lock, contentDescription = null) },
            trailingIcon = { /*TODO*/ },
            onValueChange = {
                cvvCode = it
            },
            visibleText = true,
            placeholder = "CVV"
        )
        Button(
            onClick = {
                try{
                    cvvCode.toInt()
                    onAddNewCard(cardNumber, holdersName, cvvCode, expired)
                }catch (e : Exception){

                }
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
        if(addNewCardState is AddingNewCardState.Error){
            Text(
                text = addNewCardState.e.localizedMessage,
                color = Color.Red)
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