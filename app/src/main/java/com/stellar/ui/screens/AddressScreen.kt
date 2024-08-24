package com.stellar.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.stellar.ui.theme.Grey170
import com.stellar.ui.theme.Grey241
import com.stellar.R
import com.stellar.components.TopBars.GenericTopBar
import com.stellar.data.datastore.AddressProto
import com.stellar.viewmodels.AddressViewModel
import com.stellar.data.types.Address
import com.stellar.ui.theme.PurpleFont


@Composable
fun ChooseAddressScreen(navController: NavController, viewModel: AddressViewModel){


    val addresses : List<Address> = viewModel.allAddresses

    var scroll = rememberScrollState()

    var selectedAddressState = viewModel.selectedAddress?.collectAsState(initial = null)
    var selectedAddress = selectedAddressState?.value


    var onAddressClick = { address :Address ->
        viewModel.selectAddress(address.id)
    }

    var onConfirm = {
        if(selectedAddress != null) {
            viewModel.selectAddress(selectedAddress!!.id)
            navController.navigate("Payment")
        }
    }


    Scaffold (


        topBar = { GenericTopBar(onBackClick = {navController.navigateUp()}, name = "Address")},

        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(scroll),
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
                        title = selectedAddress?.title ?: "Choose location",
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "Select Location",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    LocationBox(
                        addresses = addresses,
                        onAddressClick = onAddressClick,
                        selectedAddressId = selectedAddress?.id)
                }




                Button(
                    onClick = onConfirm,
                    colors = ButtonDefaults.buttonColors(containerColor = PurpleFont),
                    modifier = Modifier
                        .padding(top = 16.dp, start = 24.dp, end = 24.dp)
                        .fillMaxWidth()
                )
                {
                    Text("Confirm",
                        fontSize = 18.sp,
                        modifier = Modifier.padding(vertical = 10.dp))
                }
                
            }
        }

    )


}


@Composable
fun SelectedLocation(title : String?, modifier: Modifier = Modifier){


    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .border(
            2.dp,
            shape = RoundedCornerShape(16.dp),
            color = Grey241
            )

    ) {
        Row(
            modifier = Modifier
                .padding(start = 10.dp)
                .padding(vertical = 12.dp)
        ){
            Icon(
                painter = painterResource(id = R.drawable.map_marker_outline),
                contentDescription = null
            )
            Text(text = if(title == null || title.isEmpty()){ "No Adress selected"}else{title})
        }
        Icon(
            painter = painterResource(id = R.drawable.crosshairs_gps),
            contentDescription = null,
            modifier = Modifier.padding(end = 16.dp)
        )
    }
    
}


@Composable
fun LocationBox(addresses : List<Address>, selectedAddressId :  Int? = null, modifier: Modifier = Modifier, onAddressClick : (Address) -> Unit){
    
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        addresses.forEach {address ->
            Location(
                address= address,
                onClick = {onAddressClick(address)},
                modifier = Modifier.fillMaxWidth(),
                selected = selectedAddressId == address.id
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
        verticalAlignment = Alignment.CenterVertically,
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
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(3f)
        ) {
            Text(
                text = address.title,
                fontSize = 20.sp
            )
            Text(
                text = address.fullTitle,
                color = Grey170,
                fontSize = 16.sp
            )
        }
        Image(
            painter = painterResource(id = R.drawable.map),
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .weight(1f)
            )
    }
}