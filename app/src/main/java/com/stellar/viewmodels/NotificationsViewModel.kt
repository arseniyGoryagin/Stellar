package com.stellar.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stellar.data.Repository
import com.stellar.data.db.entetities.NotificationEntity
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.stellar.data.sources.NotificationsSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed interface NotificationsState{
    object Loading : NotificationsState
    object Error : NotificationsState
    data class   Success(val notifications : List<NotificationEntity>) : NotificationsState
}


@HiltViewModel
class NotificationsViewModel  @Inject constructor(private val repository: Repository) : ViewModel() {

    var notificationsState  : NotificationsState  by mutableStateOf( NotificationsState.Loading)

    val _pager = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = {
            NotificationsSource(repository =repository)
        }

    )
    val notifications = _pager.flow.cachedIn(viewModelScope)



    /*fun getNotifications(page : Int, perPage : Int = 10){
        val offset = page * perPage
        val amount = perPage
        viewModelScope.launch {
            try {
                notificationsState= NotificationsState.Loading
                val newNotifications = repository.getNotification(amount, offset)
                notificationsState= NotificationsState.Success(newNotifications)
            }catch (e : Exception) {
                notificationsState = NotificationsState.Error
            }
        }
    }*/

}