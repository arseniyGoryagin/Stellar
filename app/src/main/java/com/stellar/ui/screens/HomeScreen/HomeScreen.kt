package com.stellar.screens.HomeScreen

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.stellar.R
import com.stellar.components.BottomNavigation.BottomNavigationBar
import com.stellar.components.TopBars.CartTopBar
import com.stellar.components.TopBars.HomeTopBar
import com.stellar.components.columns.ItemColumn

import com.stellar.constants.NavItems
import com.stellar.data.UserState

import com.stellar.screens.HomeScreen.CategoryContent.CategoryContent
import com.stellar.screens.HomeScreen.HomeContent.HomeContent
import com.stellar.ui.theme.Blue51
import com.stellar.ui.theme.Grey170
import com.stellar.ui.theme.Grey204
import com.stellar.ui.theme.PurpleFont
import com.stellar.viewmodels.CartProductsState
import com.stellar.viewmodels.HomeViewModel



@SuppressLint("SuspiciousIndentation")
@Composable
fun HomeScreen(navController: NavController,  homeViewModel: HomeViewModel) {

    SideEffect {
        Log.d("RecompositionTracker", "TrackableComposable recomposed")
    }

    val scope = rememberCoroutineScope()

    println("IN HOME SCREEEN")

    val userState by homeViewModel.userState.collectAsState()
    val newArivalsState = homeViewModel.newArrivalsState
    val categoriesState = homeViewModel.categoriesState

    var selectedTab by remember {
        mutableIntStateOf(0)
    }

    var onNotifications = {
        navController.navigate("Notifications")
    }

    // TODO
    var onSearch = {
        navController.navigate("Search/${null}")
    }

    var onProfileClick = {
        navController.navigate("My Profile")
    }


    var onSeeAllProducts = {
        navController.navigate("Search/${null}"){
            launchSingleTop = true
        }
    }


    var onProductClick = {itemId  : Int ->
        navController.navigate("Product/$itemId")
    }

    var onProductFavorite = { id : Int ->
           homeViewModel.addFavorite(id)
    }

    var onProductDeFavorite = { id : Int ->
        homeViewModel.removeFavorite(id)
    }

    var onRefreshHomeContent = {
        homeViewModel.updateNewArrivals()
    }

    val tabs = listOf("Home", "Category")


    Scaffold(
        topBar = {
            when(val state = userState){
                is UserState.Success -> {
                    HomeTopBar(
                        onSearch = onSearch,
                        onNotifications = onNotifications,
                        onProfileClick = onProfileClick,
                        userImg = state.userData.avatar,
                        userName = state.userData.name
                        )
                }
                else ->{
                    CircularProgressIndicator()
                }
            }
                 },
        content = { innerPadding ->


                Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = innerPadding.calculateTopPadding(), bottom = 0.dp)
                    )
                    {
                        TabRow(
                            selectedTabIndex = selectedTab,
                            containerColor = Color.White,
                            contentColor = PurpleFont,
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp)

                        ) {

                            tabs.forEachIndexed() { index, name ->
                                Tab(
                                    text = {
                                        Text(
                                            name,
                                            fontSize = 16.sp,
                                            color = if(selectedTab == index){ PurpleFont}else{ Grey170})

                                    },
                                    selected = selectedTab == index ,
                                    onClick = { selectedTab = index },
                                )

                            }
                        }

                        AnimatedContent(targetState = selectedTab) { tabIndex ->
                            when(tabIndex){
                                0 -> HomeContent(
                                    onSeeAll = onSeeAllProducts,
                                    onProductClick = onProductClick,
                                    onProductFavorite = onProductFavorite,
                                    onProductDeFavorite = onProductDeFavorite,
                                    newArrivalsState = newArivalsState,
                                    onRefresh = onRefreshHomeContent)
                                1 -> CategoryContent(
                                    categoriesState = categoriesState
                                )
                            }
                        }
                    }
        }
    )
}











