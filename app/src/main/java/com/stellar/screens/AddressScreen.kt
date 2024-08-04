package com.stellar.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.stellar.ui.theme.Grey170
import com.stellar.ui.theme.Grey241
import com.stellar.R
import com.stellar.components.TopBars.GenericTopBar
import com.stellar.viewmodels.AddressViewModel
import com.stellar.viewmodels.PaymentViewModel
import com.stellar.data.types.Address
import com.stellar.ui.theme.PurpleFont


@Composable
fun ChooseAddressScreen(navController: NavController, viewModel: AddressViewModel){


    val addresses : List<Address> = viewModel.currentAddress


    var selectedAddress by remember {
        mutableStateOf(addresses.find {
            it.selected
        })
    }

    Scaffold (


        topBar = { GenericTopBar(onBackClick = {navController.navigateUp()}, name = "Address")},

        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {


                    Text(
                        text = "Choose your location",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Text(
                        text = "Lets find your unforgettable event. Choose a location below to get started",
                        color = Grey170,
                        fontSize = 16.sp
                    )
                    SelectedLocation(
                        title = selectedAddress?.title,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "Select Location",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    LocationBox(addresses = addresses, onAddressClick = { address ->
                        selectedAddress = address
                    }, selectedAddress = selectedAddress)
                }




                Button(
                    onClick = {
                        if(selectedAddress != null) {
                            viewModel.selectAddress(selectedAddress!!.id)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PurpleFont),
                    modifier = Modifier
                        .padding(top = 16.dp, start = 24.dp, end = 24.dp)
                        .fillMaxWidth()
                )
                {
                    Text("Confirm", fontSize = 18.sp)
                }
                
            }
        }

    )


}


@Composable
fun SelectedLocation(title : String?, modifier: Modifier = Modifier){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.border(
            1.dp,
            shape = RoundedCornerShape(16.dp),
            color = Grey241
        )
    ) {
        Row {
            Icon(
                painter = painterResource(id = R.drawable.map_marker_outline),
                contentDescription = null
            )
            Text(text = title ?: "No Adress selected")

        }
        Icon(
            painter = painterResource(id = R.drawable.crosshairs_gps),
            contentDescription = null
        )
    }
    
}


@Composable
fun LocationBox(addresses : List<Address>, selectedAddress :  Address?, modifier: Modifier = Modifier, onAddressClick : (Address) -> Unit){
    
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        addresses.forEach {address ->
            Location(
                address= address,
                onClick = {onAddressClick(address)},
                modifier = Modifier.fillMaxWidth(),
                selected = selectedAddress == address
                )
        }
    }

}

@Composable
fun Location(address : Address, onClick : () -> Unit, selected : Boolean, modifier: Modifier = Modifier){

    val borderColor = if(selected){
        PurpleFont}else{
        Grey170}

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .clickable {
                onClick()
            }
            .border(
                2.dp, borderColor, shape = RoundedCornerShape(16.dp)
            )
            .padding(10.dp)
    ){
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(3f)
        ) {
            Text(
                text = address.title,
                fontSize = 16.sp
            )
            Text(
                text = address.fullTitle,
                color = Grey170
            )
        }
        Image(
            painter = painterResource(id = R.drawable.map),
            contentDescription = null,
            modifier = Modifier.weight(1f)
            )
    }
}