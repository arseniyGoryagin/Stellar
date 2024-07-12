package com.stellar.screens.HomeScreen.CategoryContent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stellar.R
import com.stellar.components.screens.ErrorScreen
import com.stellar.components.screens.LoadingScreen
import com.stellar.data.Category
import com.stellar.viewmodels.CategoriesState
import com.stellar.viewmodels.HomeViewModel
import com.stellar.viewmodels.NewArrivalsState


@Composable
fun CategoryContent(viewmodel : HomeViewModel){
    
    val categoryItems = viewmodel.categoriesState
    

    when(categoryItems){
            is CategoriesState.Success -> CategoryCards(categories = categoryItems.categories)
            CategoriesState.Error -> ErrorScreen(message = "Error oading categories")
            CategoriesState.Loading -> LoadingScreen() }
}

@Composable 
fun CategoryCards(categories : List<Category>){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .padding(horizontal = 16.dp)
    ) {
        categories.forEach { category ->
            CategoryCard(name = category.name, imageSrc = category.image, onClick = {})
        }
    }
    
    
}