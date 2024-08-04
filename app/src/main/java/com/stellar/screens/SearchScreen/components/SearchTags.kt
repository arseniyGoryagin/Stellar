package com.stellar.screens.SearchScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ChipBorder
import androidx.compose.material3.ChipColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.stellar.ui.theme.Grey170


@Composable
fun SearchTagRow(
    onClear : (String) -> Unit,
    onClick : (String) -> Unit,
    searches : List<String>,
    modifier: Modifier){

    Row( horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = modifier) {
        searches.forEach { search ->
            InputChip(
                selected = false,
                onClick = { onClick(search) },
                label = { Text(text = search,) },
                trailingIcon = {
                    IconButton(onClick = { onClear(search) }) {
                        Icon(
                            Icons.Filled.Close,
                            contentDescription = null
                        )
                    }
                },
            )
        }
    }


}








