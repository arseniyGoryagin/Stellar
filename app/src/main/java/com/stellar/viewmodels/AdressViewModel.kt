package com.stellar.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stellar.data.Repository
import com.stellar.data.types.Address
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddressViewModel @Inject constructor(private val repository: Repository)  : ViewModel(){

        lateinit var currentAddress  : List<Address>

        init {
            viewModelScope.launch {

                    currentAddress = repository.getCurrentAdress()



            }
        }

        fun updateAddress(){
                viewModelScope.launch {
                        currentAddress = repository.getCurrentAdress()
                }
        }

        fun selectAddress(id : Int){
                viewModelScope.launch {
                        repository.selectAddress(id)
                        updateAddress()
                }
        }


}