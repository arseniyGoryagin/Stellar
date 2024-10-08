package com.stellar.screens.HomeScreen.CategoryContent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stellar.data.types.Category
import com.stellar.ui.components.screens.ErrorScreen
import com.stellar.ui.components.screens.LoadingScreen
import com.stellar.viewmodels.CategoriesState


@Composable
fun CategoryContent(categoriesState : CategoriesState){


    when(categoriesState){
            is CategoriesState.Success -> CategoryCards(categories = categoriesState.categories)
            is CategoriesState.Error -> ErrorScreen(message = "Error oading categories\n${categoriesState.e.localizedMessage}", {})
            CategoriesState.Loading -> LoadingScreen() }
}

@Composable 
fun CategoryCards(categories : List<Category>){
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .padding(horizontal = 16.dp)
    ) {
        items(categories.size){ index ->
            val category = categories[index]
            CategoryCard(name = category.name, imageSrc = category.image!!, onClick = {})
        }
    }
}