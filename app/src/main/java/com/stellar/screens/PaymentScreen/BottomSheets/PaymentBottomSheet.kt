package com.stellar.screens.PaymentScreen.BottomSheets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stellar.data.types.Card
import com.stellar.screens.PaymentScreen.AddPayment
import com.stellar.screens.PaymentScreen.CardChoice
import com.stellar.ui.theme.Grey241
import com.stellar.ui.theme.PurpleFont
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentBottomSheet(onClose : () -> Unit, onAddPayment: () -> Unit, cards : List<Card>, onDismiss: (Int) -> Unit){

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
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(cards.size, key = {index -> cards[index].id}){ index ->
                    val card = cards[index]

                    CardChoice(
                        name = card.holdersName,
                        details = card.number,
                        startChosen = chosenCardIndex == index,
                        onChosenChanged = {
                            if(it)
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
                    scope.launch { sheetState.hide() }
                    onDismiss(chosenCardIndex)
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
                    modifier = Modifier.padding(vertical = 16.dp)
                    )
            }

        }
    )
}