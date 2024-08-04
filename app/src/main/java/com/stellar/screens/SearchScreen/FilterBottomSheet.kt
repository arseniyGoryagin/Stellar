package com.stellar.screens.SearchScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RangeSlider
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
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stellar.ui.theme.Grey170
import com.stellar.ui.theme.Grey241
import com.stellar.ui.theme.PurpleFont
import com.stellar.viewmodels.SearchFilter
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    onDismiss : (SearchFilter) -> Unit,
    currentSearchFilter: SearchFilter){

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()

    var currentPriceRange = currentSearchFilter.priceRange
    var currentColor = currentSearchFilter.color
    var currenLocation = currentSearchFilter.location

    var onColorChange = { color : ColorChoice ->
        currentColor = currentColor
    }

    var onLocationChange = { locationName : String ->
        currenLocation = locationName
    }

    var onPriceRangeChange = { newRange: ClosedFloatingPointRange<Float> ->
        currentPriceRange = newRange
    }


    ModalBottomSheet(
        onDismissRequest = {onDismiss(currentSearchFilter)},
        sheetState = sheetState,
        containerColor = Color.White
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(vertical = 16.dp)
        ) {

            Text(
                text = "Filter By",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            
            PriceRange(
                priceRange = currentPriceRange,
                onValueChange = onPriceRangeChange)

            LocationPicker(
                onLocationChange = onLocationChange,
                modifier = Modifier.fillMaxWidth())

            ColorPicker(
                onColorChange = onColorChange,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    scope.launch {sheetState.hide()}
                        .invokeOnCompletion {onDismiss(SearchFilter(color = currentColor, location = currenLocation)) } },
                modifier = Modifier.fillMaxWidth()

            ) {
                Text(
                    fontSize = 20.sp,
                    text = "Apply Filter",
                    modifier = Modifier.padding(vertical = 16.dp),
                )
            }

        }

    }
}



data class ColorChoice (
    val colorName : String,
    val color : Color
)


@Composable
fun ColorPicker(onColorChange : (ColorChoice) -> Unit, modifier: Modifier = Modifier, startColor : ColorChoice? = null){



    var colorList = listOf(
        ColorChoice(color =Color.Black, colorName = "Black"),
        ColorChoice(color = PurpleFont, colorName = "Pruple"),
        ColorChoice(color =Color.Blue, colorName = "Blue"),
        ColorChoice(color =Color.Yellow, colorName = "Yellow"),
        ColorChoice(color =Color.Red, colorName = "Red"))

    var currentColor by remember{
        mutableStateOf(startColor ?: colorList[0])
    }


    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Color",
                fontSize = 16.sp
                )
            Text(
                text = currentColor.colorName,
                color = Grey170,
                fontSize = 16.sp
                )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            colorList.forEach { color ->
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(color.color)
                        .clickable {
                            currentColor = color
                            onColorChange(color)
                        }
                ) {
                    if (color == currentColor) {
                        Icon(
                            Icons.Filled.Check,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }
        }


    }

}


@Composable
fun LocationPicker(onLocationChange : (String) -> Unit, startLocation : String? = null, modifier: Modifier = Modifier){

    val scrollState = rememberScrollState()

    var locations = listOf("Amsterdam", "Moscow", "London", "New York", "Bataysk")

    var currentLocation by remember{
        mutableStateOf(startLocation ?: locations[0])
    }


    var onLocationClick = { name : String ->
        onLocationChange(name)
        currentLocation = name
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Location")
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState)
        ){
            locations.forEach { locationName ->

                val containerColor = if(locationName == currentLocation){
                    PurpleFont}else{
                    Grey241}

                val fontColor =  if(locationName == currentLocation){
                    Grey241}else{PurpleFont}

                Button(
                    shape = RoundedCornerShape(16.dp),
                    onClick = {onLocationClick(locationName)},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = containerColor,
                        ),
                    modifier = Modifier
                )
                {
                    Text(
                        text = locationName,
                        fontSize = 16.sp,
                        color = fontColor,
                        modifier = Modifier.padding(vertical = 5.dp),
                    )
                }

            }


        }
    }
}



@Composable
fun PriceRange(priceRange: ClosedFloatingPointRange<Float>, onValueChange : (ClosedFloatingPointRange<Float>) -> Unit) {

    var priceValue by remember { mutableStateOf(priceRange) }

    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(
                text = "Price",
                fontSize = 16.sp
            )
            Text(
                text = "$"+priceValue.start.toInt() + "-" + "$" +priceValue.endInclusive.toInt(),
                color = Grey170,
                fontSize = 16.sp
            )
        }
        RangeSlider(
            valueRange = SearchFilter.MIN_RANGE..SearchFilter.MAX_RANGE,
            onValueChange = { newRange ->
                onValueChange(newRange)
                priceValue = newRange
            },
            value = priceValue
        )
    }
}