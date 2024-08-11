package com.stellar.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.stellar.data.Repository
import com.stellar.data.datastore.AddressProto
import com.stellar.data.types.Address
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class AddressViewModel @Inject constructor(private val repository: Repository)  : ViewModel(){

        var selectedAddress  : Flow<AddressProto>? = null
        var allAddresses: List<Address> = emptyList()



        init {
            viewModelScope.launch {
                    updateAddress()
                    getAllAddresses()
            }
        }

        private suspend fun updateAddress(){
                selectedAddress = repository.getSelectedAddress()
        }

        private fun getAllAddresses(){
                viewModelScope.launch {
                        allAddresses = repository.getCurrentAddresses()
                }
        }

        fun selectAddress(id : Int){
                viewModelScope.launch {
                        repository.selectAddress(id)
                        updateAddress()
                }
        }


}