package com.stellar.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.stellar.components.BottomNavigation.BottomNavigationBar
import com.stellar.components.TopBars.HomeTopBar
import com.stellar.data.UserState
import com.stellar.data.datastore.UserStore
import com.stellar.screens.HomeScreen.HomeScreen
import com.stellar.screens.PaymentScreen.PaymentScreen
import com.stellar.screens.SearchScreen.SearchScreen
import com.stellar.ui.screens.AddNewCardScreen
import com.stellar.ui.screens.CartScreen
import com.stellar.ui.screens.ChangePasswordScreen
import com.stellar.ui.screens.ChooseAddressScreen
import com.stellar.ui.screens.CreateAccountScreen
import com.stellar.ui.screens.FavoriteScreen
import com.stellar.ui.screens.HelpScreen
import com.stellar.ui.screens.LanguageScreen
import com.stellar.ui.screens.LegalScreen
import com.stellar.ui.screens.LogInScreen
import com.stellar.ui.screens.MyOrderScreen
import com.stellar.ui.screens.MyProfileScreen
import com.stellar.ui.screens.NotificationsScreen
import com.stellar.ui.screens.NotificationsSettingsScreen
import com.stellar.ui.screens.ProductScreen
import com.stellar.ui.screens.SecurityScreen
import com.stellar.ui.screens.SettingsScreen
import com.stellar.ui.screens.WelcomeScreen
import com.stellar.ui.theme.StellarTheme
import com.stellar.viewmodels.AddNewCardViewModel
import com.stellar.viewmodels.AddressViewModel
import com.stellar.viewmodels.AppViewModel
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



    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun App (appViewModel : AppViewModel = hiltViewModel()){


        val userState by appViewModel.userState.collectAsState()

        StellarTheme {
            when (val state = userState) {
                is UserState.Error -> {
                    MainAppScaffold(startDestination = "Welcome")
                }
                UserState.Idle -> {
                    MainAppScaffold(startDestination = "Welcome")
                }
                UserState.Loading -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ){
                        CircularProgressIndicator()
                    }
                }
                is UserState.Success -> {
                    MainAppScaffold(startDestination = "Home")
                }
            }
        }
    }




    @Composable
    fun MainAppScaffold(startDestination : String, navController: NavHostController = rememberNavController()){



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

                        val productViewModel: ProductViewModel = hiltViewModel()

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

                        val searchViewModel: SearchViewModel = hiltViewModel()

                        SearchScreen(
                            navController = navController,
                            searchString = backStackEntry.arguments?.getString("searchString"),
                            viewModel = searchViewModel
                        )
                    }

                    composable("Home") {

                        val homeViewModel: HomeViewModel = hiltViewModel()

                        HomeScreen(navController, homeViewModel)


                    }
                    composable("Favorite") {

                        val favoritesViewModel: FavoritesViewModel = hiltViewModel()

                        FavoriteScreen(favoritesViewModel, navController)
                    }
                    composable("My Order") {

                        val orderViewModel : OrderViewModel = hiltViewModel()

                        MyOrderScreen(navController, orderViewModel)
                    }
                    composable("My Profile") {

                        val myProfileViewModel: MyProfileViewModel = hiltViewModel()

                        MyProfileScreen(navController,myProfileViewModel)
                    }
                    composable("Notifications") {

                        val notificationsViewModel : NotificationsViewModel = hiltViewModel()

                        NotificationsScreen(navController, notificationsViewModel)
                    }
                    composable("Welcome") {
                        WelcomeScreen(navController)
                    }
                    composable("Create Account") {

                        val createAccountViewModel : CreateAccountViewModel = hiltViewModel()

                        CreateAccountScreen(navController, createAccountViewModel)
                    }
                    composable("Log In") {

                        val loginViewModel : LoginViewModel = hiltViewModel()

                        LogInScreen(navController, loginViewModel )
                    }
                    composable("Settings") {

                        val settingsViewModel : SettingsViewModel = hiltViewModel()

                        SettingsScreen(navController, settingsViewModel)
                    }
                    composable("Cart") {
                        val cartViewModel: CartViewModel = hiltViewModel()
                        CartScreen(viewModel = cartViewModel, navController)
                    }
                    composable("Payment") {

                        val paymentViewModel : PaymentViewModel = hiltViewModel()

                        PaymentScreen(viewModel = paymentViewModel, navController = navController)
                    }
                    composable("Change Password") {

                        val changePasswordViewModel: ChangePasswordViewModel = hiltViewModel()

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

                        val addressViewModel : AddressViewModel = hiltViewModel()

                        ChooseAddressScreen(navController = navController, viewModel = addressViewModel)
                    }
                    composable("NewCard") {

                        val addNewCardViewModel : AddNewCardViewModel = hiltViewModel()

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



