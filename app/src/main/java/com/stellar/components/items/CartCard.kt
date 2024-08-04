package com.stellar.components.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.booleanResource
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.stellar.R
import com.stellar.ui.theme.Grey170
import com.stellar.ui.theme.Grey204
import com.stellar.ui.theme.Grey241

@Composable
fun CartCard(imgSrc : String, productQty : Int,
             productName: String, productPrice :
            Long, productColor : String,
             cartProductId: Long, onDelete : (Long) -> Unit,
             modifier: Modifier = Modifier,
             onStepUpCb: (Long) -> Unit,
             onStepDownCb: (Long) -> Unit
){


    var qty by remember {
        mutableIntStateOf(1)
    }


    var onStepUp = { value : Int->
        qty  = value
        onStepUpCb(cartProductId)
    }

    var onStepDown ={ value : Int ->
        qty  = value
        onStepDownCb(cartProductId)
    }


    Column(
        modifier = modifier
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(3f)
                .padding(bottom = 10.dp)
        ) {

            SubcomposeAsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(imgSrc)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp)),
            error = {
                Image(
                    painter = painterResource(id = R.drawable.image_broken_variant),
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
            IconButton(
                onClick = {
                    onDelete(cartProductId)
                },
                modifier = Modifier.align(Alignment.TopEnd))
            {
                Icon(
                    painter = painterResource(id = R.drawable.delete_outline),
                    contentDescription = null,
                    tint = Color.Red
                )
            }
            Stepper(
                onStepDown = onStepDown,
                onStepUp = onStepUp,
                startValue = productQty,
                minValue = 0,
                modifier = Modifier.align(Alignment.BottomStart)
            )
            }
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ){
            Column {
                Text(
                    text = productName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,

                    )
                Text(
                    text = "Color:" + productColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,

                    )

            }
            Text(
                text = "$" + (productPrice * qty).toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp

            )


        }

    }
}


@Composable
fun Stepper(onStepUp : (Int) -> Unit, onStepDown : (Int) -> Unit, startValue : Int, minValue : Int? = null, maxValue : Int? = null, modifier: Modifier = Modifier){

    var value by remember {
        mutableIntStateOf(startValue)
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(22.dp))
            .background(color = Grey241)
    ){

        Button(onClick = {
            val newValue = value -1
            if(minValue != null){
                if(newValue > minValue) {
                    value = newValue
                }
            }else{
                value = newValue
            }

            onStepDown(value)
        },  colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
           shape = CircleShape


            ){

            Text(text = "-",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )

        }
        Text(text = value.toString())
        Button(onClick = {
            val newValue = value + 1
            if(maxValue != null){
                if(newValue < maxValue) {
                    value = newValue
                }
            }else{
                value = newValue
            }
            onStepUp(value)
        
        
        },

            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            shape = CircleShape
            ) {
            Text(
                text = "+",
                fontSize = 25.sp
            )
            
        }


    }



}


