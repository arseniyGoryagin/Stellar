package com.stellar.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.stellar.components.TopBars.GenericTopBar
import com.stellar.ui.theme.Grey241
import com.stellar.ui.theme.PurpleFont
import org.intellij.lang.annotations.Language
import com.stellar.R

@Composable
fun LanguageScreen(navController : NavController){

    var chosenLanguage by remember {
        mutableStateOf("English")
    }

    Scaffold(
        topBar = { GenericTopBar(name = "Language",navController = navController) },
        content = { innerPadding ->
            Box(
                modifier =
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize()){
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 10.dp)

                ) {

                    LanguageChoice(name = "English", icon = {
                        Image(
                            modifier = Modifier.clip(CircleShape).width(40.dp),
                            contentScale = ContentScale.Fit,
                        painter = painterResource(id = R.drawable.englishflag),
                        contentDescription = null
                    ) },
                        chosen = chosenLanguage == "English", onClick ={
                        chosenLanguage = "English"
                    },
                        modifier = Modifier
                        .fillMaxWidth().height(50.dp))

                    LanguageChoice(name = "Russian", icon = {  Image(
                        modifier = Modifier.clip(CircleShape).width(40.dp),
                        contentScale = ContentScale.Fit,
                        painter = painterResource(id = R.drawable.russianflag),
                        contentDescription = null
                    ) }, chosen = chosenLanguage == "Russian", onClick ={
                        chosenLanguage = "Russian"
                    }, modifier = Modifier.fillMaxWidth().height(50.dp))
                    LanguageChoice(name = "Spanish", icon = {  Image(
                        modifier = Modifier.clip(CircleShape),
                        contentScale = ContentScale.Fit,
                        painter = painterResource(id = R.drawable.spanishflag),
                        contentDescription = null
                    ) }, chosen = chosenLanguage == "Spanish", onClick ={
                        chosenLanguage = "Spanish"
                    }, modifier = Modifier.fillMaxWidth().height(50.dp))
                    LanguageChoice(name = "Chiniese", icon = {
                        Image(
                            modifier = Modifier.clip(CircleShape),
                            contentScale = ContentScale.Fit,
                            painter = painterResource(id = R.drawable.chinieseflag),
                            contentDescription = null
                        )
                    }, chosen = chosenLanguage == "Chiniese", onClick ={
                        chosenLanguage = "Chiniese"
                    }, modifier = Modifier.fillMaxWidth().height(50.dp))

                }


            }
        },
    )

}


@Composable
fun LanguageChoice(name : String, icon : @Composable () -> Unit, chosen : Boolean, modifier: Modifier = Modifier, onClick : () -> Unit){

    val borderColor = if(chosen){
        PurpleFont}else{
        Grey241}


    Row(

        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .border(2.dp, borderColor, shape = RoundedCornerShape(20.dp))
            .clickable { onClick() }
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .padding(10.dp)

        ) {
            icon()
            Text(text = name)
        }
        if(chosen){
            Icon(
                Icons.Filled.Check,
                contentDescription = null,
                modifier = Modifier.padding(end = 10.dp)
            )
        }
    }

}


@Preview
@Composable
fun lll(){
    LanguageScreen(navController = rememberNavController())
}