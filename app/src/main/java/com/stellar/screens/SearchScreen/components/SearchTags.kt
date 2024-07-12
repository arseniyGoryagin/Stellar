package com.stellar.screens.SearchScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.stellar.ui.theme.Grey170


@Composable
fun SearchTags(onClear : (Int) -> Unit, onClick : (String) -> Unit, searches : List<String>, modifier: Modifier){
    searches.chunked(2).forEach{ searches ->
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            searches.forEachIndexed { index, search ->
                SearchTag(search, onClick, onClear ={
                    onClear(index)
                })
            }
        }
    }
}


@Composable
fun SearchTag(search : String, onClick: (String) -> Unit, onClear: () -> Unit){


    OutlinedButton(
        onClick = { onClick(search) },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Grey170

        )

    ) {
        Row(
        ) {
            Text(text = search)
            IconButton(onClick = { onClear()}) {
                Icon(Icons.Filled.Close, contentDescription = "Clear Search Tag")
            }
        }
    }


}






