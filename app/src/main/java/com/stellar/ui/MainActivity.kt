package com.stellar.ui


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
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
import com.stellar.screens.SearchScreen.SearchScreen
import com.stellar.ui.theme.StellarTheme
import com.stellar.screens.PaymentScreen.PaymentScreen
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
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        installSplashScreen()
        enableEdgeToEdge()

        setContent {
                App()
        }
    }
}

