package com.stellar


import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
//import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.stellar.components.BottomNavigation.BottomNavigationBar
import com.stellar.data.datastore.UserStore
import com.stellar.screens.HomeScreen.HomeScreen
import com.stellar.screens.MyOrderScreen
import com.stellar.screens.MyProfileScreen
import com.stellar.screens.NotificationsScreen
import com.stellar.screens.SearchScreen.SearchScreen
import com.stellar.ui.theme.StellarTheme
import com.stellar.screens.AddNewCardScreen
import com.stellar.screens.CartScreen
import com.stellar.screens.ChangePasswordScreen
import com.stellar.screens.ChooseAddressScreen
import com.stellar.screens.CreateAccountScreen
import com.stellar.screens.FavoriteScreen
import com.stellar.screens.HelpScreen
import com.stellar.screens.LanguageScreen
import com.stellar.screens.LegalScreen
import com.stellar.screens.LogInScreen
import com.stellar.screens.NotificationsSettingsScreen
import com.stellar.screens.PaymentScreen.PaymentScreen
import com.stellar.screens.ProductScreen
import com.stellar.screens.SecurityScreen
import com.stellar.screens.SettingsScreen
import com.stellar.screens.WelcomeScreen
import com.stellar.viewmodels.AddNewCardViewModel
import com.stellar.viewmodels.AddressViewModel
import com.stellar.viewmodels.CartViewModel
import com.stellar.viewmodels.ChangePasswordViewModel
import com.stellar.viewmodels.CreateAccountViewModel
import com.stellar.viewmodels.FavoritesViewModel
import com.stellar.viewmodels.HomeViewModel
import com.stellar.viewmodels.LoginViewModel
import com.stellar.viewmodels.MyProfileViewModel
import com.stellar.viewmodels.NotificationsViewModel
import com.stellar.viewmodels.OrderViewModel
import com.stellar.viewmodels.PaymentViewModel
import com.stellar.viewmodels.ProductViewModel
import com.stellar.viewmodels.SearchViewModel
import com.stellar.viewmodels.SettingsViewModel
import com.stellar.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        setContent {
                App()
        }
    }
}







@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App (){
    val context = LocalContext.current
    val userStore = UserStore(context)
    val token = userStore.getToken()
    val navController = rememberNavController()
    val startDestination = if (token != ""){"Home"}else{"Welcome"}

    StellarTheme {
        MainAppScaffold(startDestination = startDestination, navController)
    }

}




@Composable
fun MainAppScaffold(startDestination : String, navController: NavHostController){


    val userViewModel : UserViewModel = hiltViewModel()
    val homeViewModel: HomeViewModel = hiltViewModel()
    val cartViewModel: CartViewModel = hiltViewModel()
    val changePasswordViewModel: ChangePasswordViewModel = hiltViewModel()
    val myProfileViewModel: MyProfileViewModel = hiltViewModel()
    val favoritesViewModel: FavoritesViewModel = hiltViewModel()
    val productViewModel: ProductViewModel = hiltViewModel()
    val searchViewModel: SearchViewModel = hiltViewModel()
    val notificationsViewModel : NotificationsViewModel = hiltViewModel()
    val paymentViewModel : PaymentViewModel = hiltViewModel()
    val addressViewModel : AddressViewModel = hiltViewModel()
    val addNewCardViewModel : AddNewCardViewModel = hiltViewModel()
    val orderViewModel : OrderViewModel = hiltViewModel()
    val loginViewModel : LoginViewModel = hiltViewModel()
    val createAccountViewModel : CreateAccountViewModel = hiltViewModel()
    val settingsViewModel : SettingsViewModel = hiltViewModel()

    Scaffold(
        content = { padding ->
            NavHost(
                modifier = Modifier.padding(padding),
                navController = navController,
                startDestination = startDestination
            ) {
                composable(
                    route = "Product/{itemId}",
                    arguments = listOf(navArgument("itemId") { type = NavType.IntType })
                ) { backStackEntry ->
                    ProductScreen(
                        navController = navController,
                        viewmodel = productViewModel,
                        productId = backStackEntry.arguments?.getInt("itemId")
                    )
                }

                composable(
                    route = "Search/{searchString}",
                    arguments = listOf(navArgument("searchString") {
                        type = NavType.StringType
                        nullable = true
                    })
                ) { backStackEntry ->
                    SearchScreen(
                        navController = navController,
                        searchString = backStackEntry.arguments?.getString("searchString"),
                        viewModel = searchViewModel
                    )
                }

                composable("Home") {
                    HomeScreen(navController, homeViewModel, userViewModel)
                }
                composable("Favorite") {
                    FavoriteScreen(favoritesViewModel, navController)
                }
                composable("My Order") {
                    MyOrderScreen(navController, orderViewModel)
                }
                composable("My Profile") {
                    MyProfileScreen(navController, userViewModel, myProfileViewModel)
                }
                composable("Notifications") {
                    NotificationsScreen(navController, notificationsViewModel)
                }
                composable("Welcome") {
                    WelcomeScreen(navController, userViewModel)
                }
                composable("Create Account") {
                    CreateAccountScreen(navController, createAccountViewModel)
                }
                composable("Sign In") {
                    LogInScreen(navController, loginViewModel )
                }
                composable("Settings") {
                    SettingsScreen(navController, settingsViewModel)
                }
                composable("Cart") {
                    CartScreen(viewModel = cartViewModel, navController)
                }
                composable("Payment") {
                    PaymentScreen(viewModel = paymentViewModel, navController = navController)
                }
                composable("Change Password") {
                    ChangePasswordScreen(navController = navController, changePasswordViewModel)
                }
                composable("Notifications Settings") {
                    NotificationsSettingsScreen(navController)
                }
                composable("Security Settings") {
                    SecurityScreen(navController = navController)
                }
                composable("Language Settings") {
                    LanguageScreen(navController)
                }
                composable("Address") {
                    ChooseAddressScreen(navController = navController, viewModel = addressViewModel)
                }
                composable("NewCard") {
                    AddNewCardScreen(navController = navController, addNewCardViewModel)
                }
                composable("Legal and Polices") {
                    LegalScreen(navController)
                }
                composable("Help and Support") {
                    HelpScreen(navController)
                }
            }
        },
        bottomBar = {
            BottomBarLogic(navController = navController)
        }
    )
}


@Composable 
fun BottomBarLogic(navController: NavController){


    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route



    val showBottomBar = currentDestination in listOf("Favorite", "Home", "My Order", "My Profile")
    if (showBottomBar) {
        BottomNavigationBar(navController = navController)
    }
}
    






/*


  StellarTheme {

      var isFilter by remember {
          mutableStateOf(false)
      }

      Scaffold(
          modifier = Modifier.fillMaxSize(),

          //////////////////////////////////////////

          bottomBar = {
                          if(userData is UserState.Success && currentScreen != "Product/{itemId}"  && currentScreen != "Search" ){
                              BottomNavigationBar(navController = navController)
                          }
                      },

          //////////////////////////////


          topBar={
              if(userData is UserState.Success){
              println("Current screen == "+ currentScreen)
                  when(currentScreen){
                      "Home" -> HomeTopBar(navController, userViewModel)
                      "My Profile" -> ProfileTopBar(navController = navController)
                      "My Order" -> OrderTopBar(navController = navController)
                      "Favorite" ->  FavoriteTopBar(navController = navController)
                      "Notifications" -> NotificationsTopBar(navController = navController)
                      "Settings" -> SettingsTopBar(navController = navController)
                      "Cart" -> CartTopBar(navController = navController)
                      //"Product/{itemId}" -> ProductTopBar(navController = navController)
                      else -> {}
                  }
          }},


          /////////////////////////////////////

          content = {
              innerPadding ->
              val startDestination = if(userData is UserState.Success){"Home"}else{"Welcome"}

              NavHost(
                  modifier = Modifier.padding(innerPadding),
                  navController = navController,
                  startDestination = startDestination){

                  composable(
                      route = "Product/{itemId}",
                      arguments = listOf(navArgument("itemId"){type = NavType.IntType})
                  ){backStackEntry ->
                      ProductScreen(navController=navController, viewmodel = productViewModel, productId = backStackEntry.arguments?.getInt("itemId") )
                  }

                  composable(
                      route = "Search/{searchString}",
                      arguments = listOf(navArgument("searchString"){
                          type = NavType.StringType
                          nullable = true})
                  ){backStackEntry ->
                      SearchScreen(searchViewModel, navController = navController, searchString = backStackEntry.arguments?.getString("searchString"), onFilter = {
                          isFilter = true
                      } )
                  }

                  composable("Home"){
                      HomeScreen(navController,homeViewModel, userViewModel)
                  }
                  composable("Favorite"){
                     FavoriteScreen(favoritesViewModel)
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
                  composable("Cart"){
                      CartScreen(viewModel = cartViewModel)
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
      )





  }*/




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