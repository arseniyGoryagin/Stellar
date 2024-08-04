package com.stellar.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.stellar.components.TopBars.GenericTopBar
import com.stellar.R
import com.stellar.ui.theme.Grey170
import org.intellij.lang.annotations.JdkConstants.VerticalScrollBarPolicy

@Composable
fun LegalScreen(navController: NavController){

    Scaffold (
    topBar = {GenericTopBar(onBackClick = {navController.navigateUp()}, name = "Legal and Polices")},
        content = { padding ->


            val scrollState = rememberScrollState()

            Column(
                modifier = Modifier
                    .padding(padding)
                    .verticalScroll(scrollState)
                    .padding(16.dp),
            ) {
                Text(
                    text = "Terms",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(vertical = 16.dp)

                )
                Text(
                    stringResource(id = R.string.terms),
                    color = Grey170,
                    fontSize = 16.sp
                )
                Text(
                    text = "Changes to terms",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                Text(
                    stringResource(id = R.string.changesToTerms),
                    color = Grey170,
                    fontSize = 16.sp
                )
            }
        }
    )
}

