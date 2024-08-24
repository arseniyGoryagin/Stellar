package com.stellar.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stellar.data.Repository
import com.stellar.data.types.Order
import com.stellar.data.types.OrderWithProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject


sealed interface OrdersStatus{

    data class Success(val orders : List<OrderWithProduct>) : OrdersStatus
    data class Error(val e:  Exception): OrdersStatus
    object Loading : OrdersStatus
}

@HiltViewModel
class OrderViewModel @Inject constructor(private val repository: Repository): ViewModel(){


    var ordersState : OrdersStatus by mutableStateOf(OrdersStatus.Loading)


    private suspend fun clearOrders(){
        repository.clearAllOrders()
    }

    suspend fun fetchOrders(){
        ordersState = OrdersStatus.Loading
        val orders = repository.getAllOrders()
        ordersState = OrdersStatus.Success(orders)
    }

    fun updateOrders(){
        viewModelScope.launch {
            try {
                fetchOrders()
            }catch (e : retrofit2.HttpException){
                when(e.code()){
                    400 -> {
                        clearOrders()
                        fetchOrders()
                    }
                }
            }
            catch (e : Exception){
                ordersState = OrdersStatus.Error(e)
            }
        }
    }


    init {
        updateOrders()
    }



}