package com.stellar.screens

import android.webkit.WebSettings.TextSize
import androidx.annotation.FloatRange
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SheetState
import androidx.compose.material3.Slider
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
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stellar.data.SearchFilter
import com.stellar.ui.theme.Grey170
import com.stellar.viewmodels.SearchViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterModalScreen(onDismis : (ClosedFloatingPointRange<Float>) -> Unit, currentPriceRange : ClosedFloatingPointRange<Float>){

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()

    var priceRangeValue = currentPriceRange


    ModalBottomSheet(onDismissRequest = {onDismis(currentPriceRange)}, sheetState = sheetState) {

        Column(

            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)

        ) {

            Text(
                text = "Filter By",
                fontSize = 20.sp

            )
            
            PriceRange(priceRange = currentPriceRange){
                priceRangeValue = it
            }
            
            Button(
                onClick = {

                    scope.launch {sheetState.hide()}.invokeOnCompletion { if(!sheetState.isVisible){onDismis(priceRangeValue)}}
                          },
                modifier = Modifier.fillMaxWidth()

            ) {
                Text(text = "Apply Filter")
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
                text = "Price"
            )
            Text(
                text = "$"+priceValue.start.toInt() + "-" + "$" +priceValue.endInclusive.toInt(),
                color = Grey170
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