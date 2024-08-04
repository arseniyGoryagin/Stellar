package com.stellar.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.stellar.components.TopBars.OrderTopBar
import com.stellar.components.items.OrderItemCard
import com.stellar.components.items.OrderStatuses
import com.stellar.data.types.OrderWithProduct
import com.stellar.ui.theme.Grey170
import com.stellar.ui.theme.PurpleFont
import com.stellar.viewmodels.OrderViewModel
import com.stellar.viewmodels.OrdersStatus


sealed interface SelectedTab {

    abstract val index : Int
    abstract val name : String

    object MyOrderTab  : SelectedTab{
        override val index = 0
        override val name = "My Order"
    }
    object HistoryTab : SelectedTab {
        override val index = 1
        override val name = "History"
    }
}

@Composable
fun MyOrderScreen (navController: NavController, viewModel: OrderViewModel){

    LaunchedEffect(key1 = Unit) {
        viewModel.updateOrders()
    }

    val tabs = listOf(SelectedTab.MyOrderTab, SelectedTab.HistoryTab)

    val ordersState  = viewModel.ordersState

    println(ordersState)

    var selectedTab : SelectedTab by remember {
        mutableStateOf(SelectedTab.MyOrderTab)
    }




    Scaffold(modifier = Modifier.background(Color.White),
        topBar = { OrderTopBar(navController = navController) },
        content = { innerPadding ->
            Box(modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()){







                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {


                    TabRow(
                        selectedTabIndex = selectedTab.index,
                        containerColor = Color.White,
                        contentColor = PurpleFont,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                    ) {

                        tabs.forEachIndexed { index, tab ->

                            val tabColor = if (selectedTab.index == index) {
                                PurpleFont
                            } else {
                                Grey170
                            }

                            Tab(selected = selectedTab.index == index,
                                onClick = { selectedTab = tab },
                                text = {
                                    Text(
                                        text = tab.name,
                                        fontSize = 16.sp,
                                        color = tabColor
                                    )
                                }
                            )
                        }

                    }





                    when (selectedTab) {
                        SelectedTab.HistoryTab -> {
                            when (ordersState) {
                                is OrdersStatus.Error -> {
                                    Column(
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        Text(
                                            text = "Error loading orders try refreshing teh page\n" +
                                                    "${ordersState.e.localizedMessage}",
                                            color = Color.Red,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }

                                OrdersStatus.Loading -> {
                                    Column(
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                }

                                is OrdersStatus.Success -> {
                                    HistoryContent(ordersState.orders)
                                }
                            }
                        }

                        SelectedTab.MyOrderTab -> {
                            when (ordersState) {
                                is OrdersStatus.Error -> {
                                    Column(
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        Text(
                                            text = "Error loading orders try refreshing teh page\n" +
                                                    "${ordersState.e.localizedMessage}",
                                            color = Color.Red,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }

                                OrdersStatus.Loading -> {
                                    Column(
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                }

                                is OrdersStatus.Success -> {
                                    MyOrderContent(ordersState.orders)
                                }
                            }
                        }
                    }
                }

            }
        }
    )

}


@Composable
fun HistoryContent(ordersWithProduct : List<OrderWithProduct>){

    val onDetailCb = {

    }

    val onTrackingCb = {

    }


        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "No Orders Yet!",
                color = Grey170,
                textAlign = TextAlign.Center
            )
        }



}


@Composable
fun MyOrderContent(ordersWithProduct : List<OrderWithProduct>){


    val onDetailCb = {

    }

    val onTrackingCb = {

    }

    if(ordersWithProduct.isEmpty()){
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "No Completed Orders Yet!",
                color = Grey170,
                textAlign = TextAlign.Center
            )
        }
    }else{
    LazyColumn {
        items(ordersWithProduct.size) { index ->

            val order = ordersWithProduct[index].order
            val product = ordersWithProduct[index].product
            val orderStatus : OrderStatuses = when(order.status){
                1 -> OrderStatuses.Completed
                0 -> OrderStatuses.OnProgress
                else -> OrderStatuses.OnProgress
            }

            OrderItemCard(
                onDetail = onDetailCb,
                onTracking = onTrackingCb,
                imgSrc = product.images[0],
                productName = product.title,
                color = "Red",
                qty = order.qty.toString(),
                price = order.totalPrice.toString(),
                status = orderStatus,
                modifier = Modifier.padding(16.dp).height(300.dp)
            )

        }
    }


    }


}