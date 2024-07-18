package com.stellar.data

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.stellar.screens.CartScreen
import com.stellar.screens.CreateAccountScreen
import com.stellar.screens.FavoriteScreen
import com.stellar.screens.HomeScreen.HomeScreen
import com.stellar.screens.MyOrderScreen
import com.stellar.screens.MyProfileScreen
import com.stellar.screens.NotificationsScreen
import com.stellar.screens.ProductScreen
import com.stellar.screens.SearchScreen.SearchScreen
import com.stellar.screens.SettingsScreen
import com.stellar.screens.SignInScreen
import com.stellar.screens.WelcomeScreen
import com.stellar.viewmodels.CartViewModel
import com.stellar.viewmodels.FavoritesViewModel
import com.stellar.viewmodels.HomeViewModel
import com.stellar.viewmodels.ProductViewModel
import com.stellar.viewmodels.SearchViewModel
import com.stellar.viewmodels.UserViewModel

@Composable
fun AppNavGraph(navController: NavHostController, modifier: Modifier = Modifier, startDestination : String, onFilter : () -> Unit) {

    val homeViewModel : HomeViewModel = hiltViewModel()
    val searchViewModel: SearchViewModel = hiltViewModel()
    val favoritesViewModel : FavoritesViewModel = hiltViewModel()
    val productViewModel : ProductViewModel = hiltViewModel()
    val cartViewModel : CartViewModel = hiltViewModel()
    val userViewModel : UserViewModel = hiltViewModel()

    NavHost(
        modifier = modifier,
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
            SearchScreen(searchViewModel, navController = navController, searchString = backStackEntry.arguments?.getString("searchString"), onFilter = onFilter )
        }

        composable("Home"){
            HomeScreen(navController,homeViewModel, userViewModel)
        }
        composable("Favorite"){
            FavoriteScreen(favoritesViewModel, navController)
        }
        composable("My Order"){
            MyOrderScreen(navController)
        }
        composable("My Profile"){
            MyProfileScreen(navController)
        }
        composable("Notifications"){
            NotificationsScreen(navController)
        }
        composable("Welcome"){
            WelcomeScreen(navController)
        }
        composable("Create Account"){
            CreateAccountScreen(navController, userViewModel)
        }
        composable("Sign In"){
            SignInScreen(navController)
        }
        composable("Settings"){
            SettingsScreen(navController)
        }
        composable("Cart"){
            CartScreen(viewModel = cartViewModel, navController)
        }
    }


}