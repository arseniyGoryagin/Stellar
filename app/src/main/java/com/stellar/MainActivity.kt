package com.stellar

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.stellar.components.Buttons.BackButton
import com.stellar.components.Buttons.MapButton
import com.stellar.constants.NavItems
import com.stellar.screens.FavoriteScreen
import com.stellar.screens.HomeScreen
import com.stellar.screens.MainScreen
import com.stellar.screens.MyOrderScreen
import com.stellar.screens.MyProfileScreen
import com.stellar.screens.NotificationsScreen
import com.stellar.screens.SearchScreen
import com.stellar.ui.theme.StellarTheme
import com.stellar.components.Buttons.NotificationButton
import com.stellar.components.Buttons.ProfilePictureButton
import com.stellar.components.Buttons.SearchButton
import com.stellar.components.Input.SearchBar
import com.stellar.components.TopBars.FavoriteTopBar
import com.stellar.components.TopBars.HomeTopBar
import com.stellar.components.TopBars.NotificationsTopBar
import com.stellar.components.TopBars.OrderTopBar
import com.stellar.components.TopBars.ProfileTopBar
import com.stellar.components.TopBars.SearchTopBar
import com.stellar.screens.CreateAccountScreen
import com.stellar.screens.SignInScreen
import com.stellar.screens.WelcomeScreen
import com.stellar.ui.theme.Grey170
import com.stellar.ui.theme.PurpleFont

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApp()
        }
    }
}







@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(){

    val authenticated by remember { mutableStateOf(true)}


    val navController = rememberNavController()

    // moved up
    var selectedItem by remember { mutableIntStateOf(NavItems.HOME_ITEM) }


    // get the current route and observer as state
    val navControllerBackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = navControllerBackEntry?.destination?.route



    StellarTheme {


        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                if(authenticated){

                val navigationItems = listOf("Home", "My Order", "Favorite", "My Profile")

                NavigationBar(

                    containerColor = Color.White,


                ) {
                    navigationItems.forEachIndexed() { index, item ->
                        NavigationBarItem(

                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = PurpleFont,
                                selectedTextColor = PurpleFont,
                                unselectedTextColor = Grey170,
                                unselectedIconColor = Grey170
                            ),
                            //colors = NavigationBarItemColors(),


                            selected = if (selectedItem == index) {
                                true
                            } else {
                                false
                            },
                            onClick = {

                                selectedItem = index

                                when (item) {
                                    "Home" -> navController.navigate("Home")
                                    "My Order" -> navController.navigate("My Order")
                                    "Favorite" -> navController.navigate("Favorite")
                                    "My Profile" -> navController.navigate("My Profile")
                                }
                            },
                            icon = {
                                when (item) {
                                    "Home" -> Icon(
                                        Icons.Outlined.Home,
                                        contentDescription = item
                                    )

                                    "My Order" -> Icon(
                                        Icons.Outlined.LocationOn,
                                        contentDescription = item
                                    )

                                    "Favorite" -> Icon(
                                        Icons.Outlined.Favorite,
                                        contentDescription = item
                                    )

                                    "My Profile" -> Icon(
                                        Icons.Outlined.Person,
                                        contentDescription = item
                                    )
                                }
                            },
                            label = { Text(item) }
                        )
                    }
                }
            } },
            topBar={if(authenticated){

                val searchExtended by remember {mutableIntStateOf(0)}

                println("Current screen == "+ currentScreen)


                if(currentScreen != null) {
                    if (currentScreen == "Home") {
                        HomeTopBar(navController = navController)
                    }
                    if (currentScreen == "My Profile") {
                        ProfileTopBar(navController = navController)
                    }
                    if (currentScreen == "My Order") {
                        OrderTopBar(navController = navController)
                    }
                    if (currentScreen == "Favorite") {
                        FavoriteTopBar(navController = navController)
                    }
                    if (currentScreen == "Notifications") {
                        NotificationsTopBar(navController = navController)
                    }
                    if (currentScreen == "Search") {
                        SearchTopBar(navController = navController)
                    }
                }


            }}
        ) {
            innerPadding ->


            val startDestination = if(authenticated){"Home"}else{"Welcome"}


            NavHost(modifier = Modifier.padding(innerPadding),  navController = navController, startDestination = startDestination){

                composable("Home"){
                    HomeScreen()
                }
                composable("Favorite"){
                    FavoriteScreen()
                }
                composable("My Order"){
                    MyOrderScreen()
                }
                composable("My Profile"){
                    MyProfileScreen()
                }
                composable("Notifications"){
                    NotificationsScreen()
                }
                composable("Search"){
                    SearchScreen()
                }
                composable("Welcome"){
                    WelcomeScreen(navController)
                }
                composable("Create Account"){
                    CreateAccountScreen(navController)
                }
                composable("Sign In"){
                    SignInScreen(navController)
                }
            }
        }



    }


}

/*




TopAppBar(
                    title= {
                        if(currentScreen != null) {
                            if (currentScreen == "Home") {
                                Column(
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Center
                                ){
                                    Text("Hi! User1234")
                                    Text("Let's go shopping ")

                                }
                            }
                            else if (currentScreen == "Search") {
                                SearchBar {
                                    // TO DO
                                }
                            }
                            else{
                                Text(  modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center,text=currentScreen)
                            }
                        }

                    },
                    navigationIcon = {
                        if(currentScreen != null){

                                if(currentScreen == "Home"){
                                    ProfilePictureButton(){
                                        navController.navigate("My Profile")
                                        selectedItem = NavItems.PROFILE_ITEM
                                    }
                                }
                                else if(currentScreen == "My Profile" || currentScreen == "My Order" || currentScreen == "Favorite"){
                                }
                                else{
                                    BackButton(navController)
                                }
                        }

                                     },
                    actions={
                        if(currentScreen != null) {
                            if (currentScreen == "Home") {
                                SearchButton(navController = navController)
                                NotificationButton(navController)
                            } else if (currentScreen == "My Order") {
                                MapButton(navController = navController)
                            }
                            else if (currentScreen == "Favorite") {
                                NotificationButton(navController)
                            }
                            else if (currentScreen == "Search") {
                            }
                        }
                    }
                )

@Composable
fun AuthenticateScaffold(){

    Scaffold(
        modifier = Modifier.fillMaxSize(),



        bottomBar = {
            val navigationItems = listOf("Home", "My Order", "Favorite", "My Profile")

            NavigationBar {
                navigationItems.forEachIndexed() { index, item ->
                    NavigationBarItem(
                        selected = if (selectedItem == index) {
                            true
                        } else {
                            false
                        },
                        onClick = {

                            selectedItem = index

                            when (item) {
                                "Home" -> navController.navigate("Home")
                                "My Order" -> navController.navigate("My Order")
                                "Favorite" -> navController.navigate("Favorite")
                                "My Profile" -> navController.navigate("My Profile")
                            }
                        },
                        icon = {
                            when (item) {
                                "Home" -> Icon(
                                    Icons.Filled.Home,
                                    contentDescription = item
                                )

                                "My Order" -> Icon(
                                    Icons.Filled.LocationOn,
                                    contentDescription = item
                                )

                                "Favorite" -> Icon(
                                    Icons.Filled.Favorite,
                                    contentDescription = item
                                )

                                "My Profile" -> Icon(
                                    Icons.Filled.Person,
                                    contentDescription = item
                                )
                            }
                        },
                        label = { Text(item) }
                    )
                }
            }
        },
        topBar={

            val searchExtended by remember {mutableIntStateOf(0)}

            println("Current screen == "+ currentScreen)


            TopAppBar(
                title= {

                    if(currentScreen != null) {
                        if (currentScreen == "Home") {
                            Column(
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Center
                            ){
                                Text("Hi! User1234")
                                Text("Let's go shopping ")

                            }
                        }
                        else if (currentScreen == "Search") {
                            SearchBar {
                                // TO DO
                            }
                        }
                        else{
                            Text(  modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center,text=currentScreen)
                        }
                    }

                },
                navigationIcon = {
                    if(currentScreen == "Home" && currentScreen != null){
                        ProfilePictureButton(){
                            navController.navigate("My Profile")
                            selectedItem = NavItems.PROFILE_ITEM
                        }
                    }
                    else if(currentScreen == "My Profile" || currentScreen == "My Order" || currentScreen == "Favorite"){
                    }
                    else{
                        BackButton(navController)
                    }
                },
                actions={
                    if(currentScreen != null) {
                        if (currentScreen == "Home") {
                            SearchButton(navController = navController)
                            NotificationButton(navController)
                        } else if (currentScreen == "My Order") {
                            MapButton(navController = navController)
                        }
                        else if (currentScreen == "Favorite") {
                            NotificationButton(navController)
                        }
                        else if (currentScreen == "Search") {
                        }
                    }
                }
            )
        }
    ) { innerPadding ->


        val startDestination = if(authenticated){"Home"}else{"Welcome"}


        NavHost(modifier = Modifier.padding(innerPadding),  navController = navController, startDestination = startDestination){

            composable("Home"){
                HomeScreen()
            }
            composable("Favorite"){
                FavoriteScreen()
            }
            composable("My Order"){
                MyOrderScreen()
            }
            composable("My Profile"){
                MyProfileScreen()
            }
            composable("Notifications"){
                NotificationsScreen()
            }
            composable("Search"){
                SearchScreen()
            }
            composable("Welcome"){
                WelcomeScreen(navController)
            }

            /*
            composable("Sign In"){
                SignInScreen(navController)
            }
            composable("Create Account"){
                CreScreen(navController)
            }*/
        }
    }


}


@Composable
fun MainScaffold(){


        val navController = rememberNavController()

        // moved up
        var selectedItem by remember { mutableIntStateOf(NavItems.HOME_ITEM) }


        // get the current route and observer as state
        val navControllerBackEntry by navController.currentBackStackEntryAsState()
        val currentScreen = navControllerBackEntry?.destination?.route



        Scaffold(
            modifier = Modifier.fillMaxSize(),



            bottomBar = {
                val navigationItems = listOf("Home", "My Order", "Favorite", "My Profile")

                NavigationBar {
                    navigationItems.forEachIndexed() { index, item ->
                        NavigationBarItem(
                            selected = if (selectedItem == index) {
                                true
                            } else {
                                false
                            },
                            onClick = {

                                selectedItem = index

                                when (item) {
                                    "Home" -> navController.navigate("Home")
                                    "My Order" -> navController.navigate("My Order")
                                    "Favorite" -> navController.navigate("Favorite")
                                    "My Profile" -> navController.navigate("My Profile")
                                }
                            },
                            icon = {
                                when (item) {
                                    "Home" -> Icon(
                                        Icons.Filled.Home,
                                        contentDescription = item
                                    )

                                    "My Order" -> Icon(
                                        Icons.Filled.LocationOn,
                                        contentDescription = item
                                    )

                                    "Favorite" -> Icon(
                                        Icons.Filled.Favorite,
                                        contentDescription = item
                                    )

                                    "My Profile" -> Icon(
                                        Icons.Filled.Person,
                                        contentDescription = item
                                    )
                                }
                            },
                            label = { Text(item) }
                        )
                    }
                }
            },
            topBar={

                val searchExtended by remember {mutableIntStateOf(0)}

                println("Current screen == "+ currentScreen)


                TopAppBar(
                    title= {

                        if(currentScreen != null) {
                            if (currentScreen == "Home") {
                                Column(
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Center
                                ){
                                    Text("Hi! User1234")
                                    Text("Let's go shopping ")

                                }
                            }
                            else if (currentScreen == "Search") {
                                SearchBar {
                                    // TO DO
                                }
                            }
                            else{
                                Text(  modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center,text=currentScreen)
                            }
                        }

                    },
                    navigationIcon = {
                        if(currentScreen == "Home" && currentScreen != null){
                            ProfilePictureButton(){
                                navController.navigate("My Profile")
                                selectedItem = NavItems.PROFILE_ITEM
                            }
                        }
                        else if(currentScreen == "My Profile" || currentScreen == "My Order" || currentScreen == "Favorite"){
                        }
                        else{
                            BackButton(navController)
                        }
                    },
                    actions={
                        if(currentScreen != null) {
                            if (currentScreen == "Home") {
                                SearchButton(navController = navController)
                                NotificationButton(navController)
                            } else if (currentScreen == "My Order") {
                                MapButton(navController = navController)
                            }
                            else if (currentScreen == "Favorite") {
                                NotificationButton(navController)
                            }
                            else if (currentScreen == "Search") {
                            }
                        }
                    }
                )
            }
        ) { innerPadding ->


            val startDestination = if(authenticated){"Home"}else{"Welcome"}


            NavHost(modifier = Modifier.padding(innerPadding),  navController = navController, startDestination = startDestination){

                composable("Home"){
                    HomeScreen()
                }
                composable("Favorite"){
                    FavoriteScreen()
                }
                composable("My Order"){
                    MyOrderScreen()
                }
                composable("My Profile"){
                    MyProfileScreen()
                }
                composable("Notifications"){
                    NotificationsScreen()
                }
                composable("Search"){
                    SearchScreen()
                }
                composable("Welcome"){
                    WelcomeScreen(navController)
                }

                /*
                composable("Sign In"){
                    SignInScreen(navController)
                }
                composable("Create Account"){
                    CreScreen(navController)
                }*/
            }
        }
}*/