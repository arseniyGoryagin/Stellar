package com.stellar.ui.components.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.room.util.TableInfo
import com.stellar.ui.theme.Grey170

@Composable
fun ErrorScreen(message : String, onRefresh : () -> Unit){


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = message,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            color = Grey170
            )
        Spacer(modifier = Modifier.height(20.dp))
        IconButton(
            modifier = Modifier
                .height(50.dp)
                .width(50.dp),
            onClick = onRefresh) {
            Icon(
                Icons.Outlined.Refresh,
                tint = Grey170,
                contentDescription = null,
                modifier = Modifier.fillMaxSize())
        }
    }
}