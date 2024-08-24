package com.stellar.screens.PaymentScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.stellar.components.TopBars.GenericTopBar
import com.stellar.ui.theme.PurpleFont
import com.stellar.R
import com.stellar.components.items.PaymentItemCard
import com.stellar.data.datastore.CardProto
import com.stellar.data.types.Card
import com.stellar.data.types.CartProductWithProduct
import com.stellar.screens.PaymentScreen.BottomSheets.OrderSuccessfullBottomSheet
import com.stellar.screens.PaymentScreen.BottomSheets.PaymentBottomSheet
import com.stellar.ui.screens.PaymentScreen.BottomSheets.ErrorBottomSheet
import com.stellar.ui.theme.Grey170
import com.stellar.ui.theme.Grey241
import com.stellar.viewmodels.CardState
import com.stellar.viewmodels.CartProductsState
import com.stellar.viewmodels.MakingOrderState
import com.stellar.viewmodels.PaymentViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun PaymentScreen(navController: NavController, viewModel: PaymentViewModel) {

        val cartProducts = viewModel.cartProducts
        val cardState = viewModel.cardsState

        val selectedAddressState = viewModel.selectedAddress?.collectAsState(initial = null)
        val selectedAddress = selectedAddressState?.value

        val selectedCardState = viewModel.selectedCard?.collectAsState(initial = null)
        val selectedCard = selectedCardState?.value

        val makeOrderState = viewModel.makingOrderState

        var totalPrice: String = ""
        val verticalScrollState = rememberScrollState()

        // Bottom sheets
        var showBottomSheet by remember {
            mutableStateOf(false)
        }
        var showChooseAddess by remember {
        mutableStateOf(false)
        }
        var showChooseCard by remember {
        mutableStateOf(false)
        }
        var orderSuccessfullBottomSheet by remember {
            mutableStateOf(false)
        }


        var onAddPaymentPaymentBottomSheet = {
            navController.navigate("NewCard")
        }

        var onConfirmPaymentBottomSheet ={ card : Card ->
            showBottomSheet = false
            viewModel.selectCard(card.id)
        }

        var onAddPayment = {
            showBottomSheet = true
        }

        var onCheckOut = {
            if(selectedAddressState == null || selectedAddress?.title == ""){
                showChooseAddess = true
            }
            else if(selectedCardState == null || selectedCard?.holdersName == ""){
                showChooseCard = true
            }
            else {
                println("start making order")
                viewModel.makeOrder()
            }
        }

        var onDismissOrderSuccessfull = {
            navController.navigate("My Order")
            viewModel.restOrderState()
        }

        var onCloseOrderSuccessfull = {
            navController.navigate("My Order")
            viewModel.restOrderState()
        }

        var onEditAddress = {
            navController.navigate("Address")
        }


        var onChooseAddressDialog = {
            showChooseAddess = false
            navController.navigate("Address")
        }

        var onChoosePaymentDialog = {
            showChooseCard = false
            navController.navigate("NewCard")
        }



        var onDismissErrorSheet = {
            viewModel.restOrderState()
        }


        Scaffold(

            topBar = { GenericTopBar(onBackClick = {navController.navigate("Cart")}, name = "Payment") },
            content = { padding ->


                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(horizontal = 16.dp)
                        .verticalScroll(verticalScrollState)
                )
                {
                    Spacer(modifier = Modifier.height(16.dp))



                        // Address
                        AddressBox(
                            onEditAddress = onEditAddress,
                            address = selectedAddress?.title,
                            modifier = Modifier
                                .fillMaxWidth()
                        )


                        // Products
                        when (cartProducts) {
                            CartProductsState.Loading -> {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxWidth()
                                ){
                                    CircularProgressIndicator()
                                }
                            }

                            is CartProductsState.Success -> {

                                ProductsColumn(
                                    list = cartProducts.products,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(400.dp)
                                )


                                totalPrice = cartProducts.products.sumOf { cartProductWithProduct ->
                                    cartProductWithProduct.cartProduct.qty * cartProductWithProduct.product.price
                                }.toString()


                            }

                            is CartProductsState.Error -> {
                                Text(text = "Error loading cart products\n${cartProducts.error.localizedMessage}")
                            }
                        }


                    // Cards
                    PaymentDetails(
                        totalPrice = totalPrice,
                        onPayment = onAddPayment,
                        onCheckOut = onCheckOut,
                        modifier = Modifier
                            .fillMaxWidth(),
                        chosenCard = selectedCard
                    )


                    if (showBottomSheet) {
                        PaymentBottomSheet(
                            onClose = { showBottomSheet = false },
                            onAddPayment = onAddPaymentPaymentBottomSheet,
                            cardsState = cardState,
                            onConfirmPayment = onConfirmPaymentBottomSheet
                        )
                    }


                    if (makeOrderState is MakingOrderState.Success) {
                        OrderSuccessfullBottomSheet(
                            onClose = onCloseOrderSuccessfull,
                            onDismiss = onDismissOrderSuccessfull,
                        )
                    }

                    if (makeOrderState is MakingOrderState.Error) {
                        ErrorBottomSheet(
                            error = makeOrderState.error.localizedMessage,
                            onDismiss = onDismissErrorSheet
                        )
                    }





                    // Error dialogs
                    if(showChooseAddess){
                        ErrorDialog(onDismiss = { showChooseAddess = false},
                            text = "Please choose an address before you can order",
                            onButtonClick = onChooseAddressDialog,
                            buttonText = "Choose Address")
                    }
                    if(showChooseCard){
                        ErrorDialog(
                            onDismiss = { showChooseCard = false},
                            text = "Please choose a payment method before you can order",
                            buttonText = "Choose payment",
                            onButtonClick = onChoosePaymentDialog)
                    }


                }
            }


        )
    }






@Composable
fun PaymentDetails(
    totalPrice : String,
                       modifier: Modifier = Modifier,
                       onPayment: () -> Unit,
                       onCheckOut : () -> Unit,
                       chosenCard : CardProto?){



        println("IDD D  D  D D == " + chosenCard?.id)
        println("NAME == " + chosenCard?.number)

        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(16.dp)

        ) {
            Text(
                text = "Payment Detailes",
                fontWeight = FontWeight.Bold

            )

            if(chosenCard != null && chosenCard.holdersName != "") {
                CardBox(
                    onClick = onPayment,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    cardNumber = chosenCard.number,
                    cardName = chosenCard.holdersName
                )
            }
            else{
                AddPayment (
                    onClick = onPayment,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Total amount",
                    fontSize = 16.sp,
                    color = Grey170
                )
                Text(text = "$${totalPrice}")
            }
            Button(
                onClick = onCheckOut    ,
                colors = ButtonDefaults.buttonColors(containerColor = PurpleFont),
                modifier = Modifier
                    .padding(top = 16.dp, start = 24.dp, end = 24.dp)
                    .fillMaxWidth()
            )
            {
                Text("Check out now",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 10.dp))
            }

        }


    }

@Composable
fun AddressBox(onEditAddress : () -> Unit,  address : String?, modifier: Modifier = Modifier){



        Column(
            modifier = modifier
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    "Address",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Edit",
                    color = PurpleFont,
                    fontSize = 12.sp,
                    modifier = Modifier.clickable {
                        onEditAddress()
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ){
                Image(painter = painterResource(id = R.drawable.map), contentDescription = null)
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "House",
                        fontSize = 16.sp

                    )
                    Text(
                        text= if(address == null || address == ""){"Please select and address"}else{address},
                        fontSize = 12.sp,
                        color = Grey170

                    )
                }
            }

        }
    }


@Composable
fun ProductsColumn(list: List<CartProductWithProduct>, modifier: Modifier = Modifier){

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier
        ) {
            Text(
                text = "Products(${list.size})",
                fontWeight = FontWeight.Bold
            )
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                items(list.size){ index ->
                    val product  = list[index].product
                    val qty = list[index].cartProduct.qty

                    PaymentItemCard(
                        imgSrc = product.images[0],
                        itemName = product.title,
                        itemPrice = (product.price * qty).toString(),
                        modifier = Modifier.height(100.dp)
                    )
                }

            }
        }
    }






@Composable
fun CardBox(onClick : () -> Unit,
            cardName : String,
            cardNumber : String, modifier: Modifier = Modifier){


        println("CARD NUMBEEBEBE +++ " + cardNumber)

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .border(1.dp, Grey170, shape = RoundedCornerShape(16.dp))
                .clickable { onClick() }
                .fillMaxWidth()
                .padding(16.dp)
        ){
            Row(

                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),

            ){
                Icon(painter = painterResource(id = R.drawable.credit_card_outline), contentDescription = null)
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(text = cardName)
                    Text(text = formatCardNumber(cardNumber))
                }
            }
            Icon(Icons.Filled.KeyboardArrowRight, contentDescription = null)
        }


}


fun formatCardNumber(details : String) : String{
    return details.chunked(4).joinToString (" " )
}






@Composable
fun AddPayment(modifier: Modifier = Modifier, onClick: () -> Unit){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .background(Color.White)
            .padding(16.dp)
    ){
        Icon(painter = painterResource(id = R.drawable.plus), contentDescription = null)
        Text(text = stringResource(id = R.string.add_payment))
    }
}



@Composable
fun ErrorDialog(onDismiss : () -> Unit, text : String, buttonText : String, onButtonClick : () -> Unit){
    Dialog(onDismissRequest = onDismiss) {
        Card(
            colors = CardColors(
                disabledContentColor = Color.White,
                containerColor = Color.White,
                 contentColor = Color.White,
                disabledContainerColor = Color.White
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)

        ){
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize().padding(16.dp)
            ) {
                Text(
                    text = text,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
                Button(
                    onClick = onButtonClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    modifier = Modifier
                        .padding(top = 16.dp, start = 24.dp, end = 24.dp)
                        .fillMaxWidth()
                ){
                    Text(text = buttonText,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(vertical = 10.dp),
                        color = PurpleFont
                    )
                }
            }
        }
    }
}

