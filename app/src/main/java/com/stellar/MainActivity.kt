package com.stellar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.stellar.constants.NavItems
import com.stellar.screens.FavoriteScreen
import com.stellar.screens.HomeScreen.HomeScreen
import com.stellar.screens.MyOrderScreen
import com.stellar.screens.MyProfileScreen
import com.stellar.screens.NotificationsScreen
import com.stellar.screens.SearchScreen.SearchScreen
import com.stellar.ui.theme.StellarTheme
import com.stellar.components.TopBars.FavoriteTopBar
import com.stellar.components.TopBars.HomeTopBar
import com.stellar.components.TopBars.NotificationsTopBar
import com.stellar.components.TopBars.OrderTopBar
import com.stellar.components.TopBars.ProfileTopBar
import com.stellar.components.TopBars.SearchTopBar
import com.stellar.components.TopBars.SettingsTopBar
import com.stellar.screens.CreateAccountScreen
import com.stellar.screens.FilterModalScreen
import com.stellar.screens.SettingsScreen
import com.stellar.screens.SignInScreen
import com.stellar.screens.WelcomeScreen
import com.stellar.ui.theme.Grey170
import com.stellar.ui.theme.PurpleFont
import com.stellar.viewmodels.FavoriteViewModel
import com.stellar.viewmodels.HomeViewModel
import com.stellar.viewmodels.SearchViewModel
import com.stellar.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
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

    // view models
    val homeViewModel : HomeViewModel = hiltViewModel()
    val userViewModel : UserViewModel = hiltViewModel()
    val searchViewModel: SearchViewModel = hiltViewModel()
    val favoriteViewModel : FavoriteViewModel = hiltViewModel()


    StellarTheme {

        var isFilter by remember {
            mutableStateOf(false)
        }

        Scaffold(
            modifier = Modifier.fillMaxSize(),

            //////////////////////////////////////////

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

            //////////////////////////////


            topBar={if(authenticated){

                val searchExtended by remember {mutableIntStateOf(0)}

                println("Current screen == "+ currentScreen)


                if(currentScreen != null) {
                    if (currentScreen == "Home") {
                        HomeTopBar(navController, userViewModel)
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
                        SearchTopBar(navController = navController, searchViewModel){
                            isFilter = true

                        }
                    }
                    if (currentScreen == "Settings") {
                        SettingsTopBar(navController = navController)
                    }
                }


            }}
        )


        /////////////////////////////////////
        {
            innerPadding ->





            val startDestination = if(authenticated){"Home"}else{"Welcome"}




            NavHost(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                startDestination = startDestination){

                composable("Home"){
                    HomeScreen(homeViewModel, userViewModel)
                }
                composable("Favorite"){
                    FavoriteScreen(favoriteViewModel)
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
                    SearchScreen(searchViewModel)
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
                composable("Settings"){
                    SettingsScreen()
                }
            }


            if(isFilter){
                FilterModalScreen(
                    currentPriceRange = searchViewModel.searchFilter.price_min .. searchViewModel.searchFilter.price_max,
                    onDismis = {
                        searchViewModel.updatePriceRange(it)
                        isFilter = false
                    }
                )
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