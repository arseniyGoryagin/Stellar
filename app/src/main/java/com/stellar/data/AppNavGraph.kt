package com.stellar.data

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.stellar.screens.CartScreen
import com.stellar.screens.ChangePasswordScreen
import com.stellar.screens.CreateAccountScreen
import com.stellar.screens.FavoriteScreen
import com.stellar.screens.HelpScreen
import com.stellar.screens.HomeScreen.HomeScreen
import com.stellar.screens.LanguageScreen
import com.stellar.screens.LegalScreen
import com.stellar.screens.MyOrderScreen
import com.stellar.screens.MyProfileScreen
import com.stellar.screens.NotificationsScreen
import com.stellar.screens.NotificationsSettingsScreen
import com.stellar.screens.ProductScreen
import com.stellar.screens.SearchScreen.SearchScreen
import com.stellar.screens.SecurityScreen
import com.stellar.screens.SettingsScreen
import com.stellar.screens.WelcomeScreen
import com.stellar.viewmodels.CartViewModel
import com.stellar.viewmodels.ChangePasswordViewModel
import com.stellar.viewmodels.FavoritesViewModel
import com.stellar.viewmodels.HomeViewModel
import com.stellar.viewmodels.MyProfileViewModel
import com.stellar.viewmodels.ProductViewModel
import com.stellar.viewmodels.SearchViewModel
import com.stellar.viewmodels.UserViewModel
/*
@Composable
fun AppNavGraph(navController: NavHostController, modifier: Modifier = Modifier, startDestination : String, onFilter : () -> Unit) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination)

    {

        composable(
            route = "Product/{itemId}",
            arguments = listOf(navArgument("itemId"){type = NavType.IntType})
        ){backStackEntry ->
            val productViewModel : ProductViewModel = hiltViewModel()
            ProductScreen(navController=navController, viewmodel = productViewModel, productId = backStackEntry.arguments?.getInt("itemId") )
        }

        composable(
            route = "Search/{searchString}",
            arguments = listOf(navArgument("searchString"){
                type = NavType.StringType
                nullable = true})
        ){backStackEntry ->
            val searchViewModel: SearchViewModel = hiltViewModel()
            SearchScreen(navController = navController, searchString = backStackEntry.arguments?.getString("searchString"), viewModel = searchViewModel, onFilter = onFilter )
        }

        composable("Home"){
            val homeViewModel : HomeViewModel = hiltViewModel()
            val userViewModel : UserViewModel = hiltViewModel()
            HomeScreen(navController,homeViewModel, userViewModel)
        }
        composable("Favorite"){
            val favoritesViewModel : FavoritesViewModel = hiltViewModel()
            FavoriteScreen(favoritesViewModel, navController)
        }
        composable("My Order"){
            MyOrderScreen(navController)
        }
        composable("My Profile"){
            val userViewModel : UserViewModel = hiltViewModel()
            val myProfileViewModel : MyProfileViewModel = hiltViewModel()
            MyProfileScreen(navController, userViewModel, myProfileViewModel)
        }
        composable("Notifications"){
            NotificationsScreen(navController)
        }
        composable("Welcome"){
            val userViewModel : UserViewModel = hiltViewModel()
            WelcomeScreen(navController, userViewModel)
        }
        composable("Create Account"){
            val userViewModel : UserViewModel = hiltViewModel()
            CreateAccountScreen(navController, userViewModel)
        }
        composable("Sign In"){
            val userViewModel : UserViewModel = hiltViewModel()
            SignInScreen(navController, userViewModel)
        }
        composable("Settings"){
            val userViewModel : UserViewModel = hiltViewModel()
            SettingsScreen(navController, userViewModel)
        }
        composable("Cart"){
            val cartViewModel : CartViewModel = hiltViewModel()
            CartScreen(viewModel = cartViewModel, navController)
        }
        composable("Change Password"){
            val changePasswordViewModel : ChangePasswordViewModel = hiltViewModel()
            ChangePasswordScreen(  navController = navController, changePasswordViewModel)
        }
        composable("Notifications Settings"){
            NotificationsSettingsScreen(navController)
        }
        composable("Security Settings"){
            SecurityScreen(navController = navController)
        }
        composable("Language Settings"){
            LanguageScreen(navController)
        }
        composable("Legal and Polices"){
            LegalScreen(navController)
        }
        composable("Help and Support"){
            HelpScreen(navController)
        }

    }


}*/