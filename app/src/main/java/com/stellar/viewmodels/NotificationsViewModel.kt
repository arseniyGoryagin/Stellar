package com.stellar.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stellar.data.Repository
import com.stellar.data.db.entetities.Notification
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed interface NotificationsState{
    object Loading : NotificationsState
    object Error : NotificationsState
    object Success : NotificationsState
}


@HiltViewModel
class NotificationsViewModel  @Inject constructor(private val repository: Repository) : ViewModel() {

    var notifications : List<Notification> = emptyList()
    var notificationsState  : NotificationsState=  NotificationsState.Loading


    init {
        getNotifications(0, 10)
    }


    fun getNotifications(page : Int, perPage : Int = 10){
        val offset = page * perPage
        val amount = perPage

        viewModelScope.launch {
            try {
                notificationsState= NotificationsState.Loading
                val newNotifications = repository.getNotification(amount, offset)
                notifications = newNotifications
                notificationsState= NotificationsState.Success
            }catch (e : Exception){

                notificationsState= NotificationsState.Loading

            }
        }
    }

}