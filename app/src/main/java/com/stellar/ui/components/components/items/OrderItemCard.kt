package com.stellar.components.items

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.stellar.R
import com.stellar.ui.theme.Grey170
import com.stellar.ui.theme.Grey204
import com.stellar.ui.theme.PurpleFont
import okhttp3.internal.wait


sealed interface OrderStatuses{
    object OnProgress : OrderStatuses
    object Completed : OrderStatuses
}



@Composable
fun OrderItemCard( onDetail : () -> Unit,
                   onTracking : () -> Unit,
                   imgSrc : String,
                   productName : String,
                   qty : String,
                   price : String,
                   status : OrderStatuses,
                   modifier: Modifier = Modifier){
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .border(1.dp, color = Grey204, shape = RoundedCornerShape(16.dp))
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .padding(horizontal = 14.dp).padding(top = 14.dp)
                .weight(2f)
        ){

            SubcomposeAsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(imgSrc)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(16.dp)),
                error = {
                    Image(
                        painter = painterResource(id = R.drawable.noimage),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                    )
                },
                loading = {
                    Box(

                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            )

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.weight(1f).fillMaxHeight()
            ) {
                Text(
                    text = productName,
                    fontSize = 20.sp
                    )
                Text(
                    text = "Qty: ${qty}",
                    fontSize = 16.sp
                    )

            }
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement =Arrangement.SpaceBetween,
                modifier = Modifier.weight(1f).fillMaxHeight()
            ) {

                val statusText = when(status){
                    OrderStatuses.Completed -> "Completed"
                    OrderStatuses.OnProgress -> "In Progress"
                }

                val stutusColor =  when(status){
                    OrderStatuses.Completed -> Color.Green
                    OrderStatuses.OnProgress -> Color.Cyan
                }
                Text(
                    text = statusText,
                    color = stutusColor,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .border(1.dp, stutusColor, shape = RoundedCornerShape(12.dp))
                        .padding(horizontal = 10.dp).padding(vertical = 5.dp)
                    )
                Text(
                    text = "$${price}",
                    modifier = Modifier.padding(bottom = 16.dp))
            }

        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(14.dp)
        ){
            Button(
                border = BorderStroke(1.dp, Grey204),
                onClick = onDetail,
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                modifier = Modifier
                    .weight(1f)
            )
            {
                Text("Detail",
                    fontSize = 18.sp,
                    color = Color.Black,)
            }
            Button(
                onClick = onTracking,
                colors = ButtonDefaults.buttonColors(containerColor = PurpleFont),
                modifier = Modifier
                    .weight(1f)
            )
            {
                Text("Tracking",
                    fontSize = 18.sp,)
            }

        }



    }



}

/*




*/