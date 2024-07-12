package com.stellar.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stellar.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    var userData = repository.userData


    fun updateUserData(){
        viewModelScope.launch(Dispatchers.IO) {
                repository.updateUserData()
        }
    }


}