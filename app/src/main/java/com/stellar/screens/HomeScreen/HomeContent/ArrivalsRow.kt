package com.stellar.screens.HomeScreen.HomeContent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stellar.ui.theme.PurpleFont

@Composable
fun ArrivalsRow(onSeeAll : () -> Unit){

    Row(

        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Text(
            text = "New Arrivals",
            modifier = Modifier.padding(16.dp),
            fontSize = 20.sp
        )
        Text(
            text = "See All",
            modifier = Modifier.padding(16.dp)
                .clickable{onSeeAll()},
            fontSize = 16.sp,
            color = PurpleFont
        )

    }
}