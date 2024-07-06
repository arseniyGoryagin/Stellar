package com.stellar.viewmodels

import androidx.lifecycle.ViewModel
import com.stellar.data.ItemEntity
import com.stellar.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val repository : Repository) : ViewModel(){
    var newArivals : List<ItemEntity> = repository.getNewArrilvals()
}