package com.stellar.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.stellar.R
import com.stellar.components.TopBars.GenericTopBar
import com.stellar.ui.theme.Grey170
import com.stellar.ui.theme.Grey241

@Composable
fun HelpScreen(navController : NavController){
    Scaffold (
        topBar = { GenericTopBar(onBackClick = {navController.navigateUp()}, name = "Help & Support") },
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                HelpContent()
            }
        }
    )
}

@Composable
fun ExpandableRow(name : String, expandedText : String , modifier: Modifier = Modifier){

    var isExpanded by remember{
        mutableStateOf(false)
    }

    Column(
        modifier = modifier.clickable { isExpanded = !isExpanded },
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {


        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()

        ) {

            Text(
                text = name,
                fontSize = 20.sp
            )

            if (!isExpanded) {
                Icon(Icons.Filled.KeyboardArrowUp, contentDescription = null)
            }else{
                Icon(Icons.Filled.KeyboardArrowDown, contentDescription = null)
            }
        }
        if(isExpanded){
            Text(
                text =expandedText,
                color = Grey170,
                fontSize = 16.sp
            )
        }
    }

}

@Composable
fun HelpContent(){


    val totalNumber = 5

    val scrollState = rememberScrollState()

    Column(

        modifier = Modifier.verticalScroll(scrollState)

    ) {

        for(index in 0..5){
            ExpandableRow(
                name = "Lorem Ipsum dolor sit amet",
                expandedText = stringResource(id = R.string.helpAndSupportExpandedText),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(vertical = 32.dp))
            HorizontalDivider(
                color = Grey241,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

    }


}