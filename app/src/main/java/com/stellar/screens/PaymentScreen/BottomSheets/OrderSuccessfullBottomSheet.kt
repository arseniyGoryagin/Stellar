package com.stellar.screens.PaymentScreen.BottomSheets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stellar.data.types.Card
import com.stellar.screens.PaymentScreen.AddPayment
import com.stellar.screens.PaymentScreen.CardChoice
import com.stellar.ui.theme.Grey241
import com.stellar.ui.theme.PurpleFont
import com.stellar.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderSuccessfullBottomSheet(
    onClose : () -> Unit,
    onDismiss: () -> Unit){

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()


    ModalBottomSheet(
        onDismissRequest = onClose,
        containerColor = Color.White,
        sheetState = sheetState,

        content = {

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ordersucc),
                    contentDescription = null,
                    modifier = Modifier
                        .height(200.dp)
                        .width(200.dp)
                        .clip(CircleShape)
                )
                    Text(
                        text = "Order Successfull",
                        fontWeight = FontWeight.Bold

                    )
                    Text(
                        text = "Your Order will be packed by teh clerk, will arrice at your house in 3 to 4 days",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 16.dp)
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
                    Text("Order Tracking",
                        fontSize = 18.sp,
                        modifier = Modifier.padding(vertical = 16.dp)
                        )
                }
            }


        }
    )

}