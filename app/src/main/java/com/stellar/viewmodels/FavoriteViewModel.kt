package com.stellar.viewmodels

import android.view.View
import androidx.lifecycle.ViewModel
import com.stellar.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

}