package com.stellar.screens.PaymentScreen.BottomSheets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
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
import com.stellar.R
import com.stellar.data.types.Card
import com.stellar.screens.PaymentScreen.AddPayment
import com.stellar.screens.PaymentScreen.formatCardNumber
import com.stellar.ui.theme.Grey241
import com.stellar.ui.theme.PurpleFont
import com.stellar.viewmodels.CardState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentBottomSheet(
                        onClose : () -> Unit,
                        onAddPayment: () -> Unit,
                        cardsState : CardState,
                        onConfirmPayment : (Card) -> Unit
){

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    var chosenCardIndex by remember {
        mutableStateOf(0)
    }


    ModalBottomSheet(
        onDismissRequest = { onClose() },
        modifier = Modifier
            .padding(vertical = 16.dp),
        containerColor = Grey241,
        sheetState = sheetState,
        content = {
            Text(
                text = "Payment Method",
                modifier = Modifier.padding(16.dp),
                fontWeight = FontWeight.Bold

            )



                when(cardsState){

                    is CardState.Success ->{
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(
                                cardsState.cards.size,
                                key = { index -> cardsState.cards[index].id }) { index ->
                                val card = cardsState.cards[index]

                                println("Card choice  + " +index + "  chosen index = " + chosenCardIndex)
                                CardChoice(
                                    name = card.holdersName,
                                    details = card.number,
                                    chosen = chosenCardIndex == index,
                                    onChosenChanged = {
                                        if (it)
                                            chosenCardIndex = index
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp)
                                )
                            }
                        }
                        AddPayment(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            onClick ={scope.launch { sheetState.hide() }
                                onAddPayment()
                            })
                        Button(
                            onClick = {
                                scope.launch { sheetState.hide() }.invokeOnCompletion {
                                    onConfirmPayment(cardsState.cards[chosenCardIndex])
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = PurpleFont),
                            modifier = Modifier
                                .padding(top = 16.dp, bottom = 16.dp, start = 24.dp, end = 24.dp)
                                .fillMaxWidth()
                        )
                        {
                            Text(
                                text = "Confirm Payment",
                                fontSize = 18.sp,
                                modifier = Modifier.padding(vertical = 10.dp)
                            )
                        }

                    }
                    is CardState.Loading -> {

                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                CircularProgressIndicator()
                            }

                    
                    }
                    is CardState.Error -> {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = "Error loading cards")
                            }
                    }
                }
        }
    )
}



@Composable
fun CardChoice(name : String, details : String, chosen : Boolean, onChosenChanged : (Boolean) -> Unit, modifier: Modifier = Modifier){


    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)

    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(start = 10.dp)
                .padding(vertical = 16.dp)
        ){
            Icon(painter = painterResource(id = R.drawable.credit_card_outline), contentDescription = null)
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ){
                Text(text = name)
                Text(
                    text = formatCardNumber(details),
                )
            }
        }
        Checkbox(
            checked = chosen,
            onCheckedChange = {
                onChosenChanged(it)
            },
        )
    }

}
