package com.stellar.data.sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.stellar.data.Repository
import com.stellar.data.db.entetities.NotificationEntity
import com.stellar.data.types.Notification


class NotificationsSource(private val repository: Repository): PagingSource<Int, NotificationEntity>() {


    override fun getRefreshKey(state: PagingState<Int, NotificationEntity>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NotificationEntity> {

        val pageNumber = params.key ?: 1
        val perPage = params.loadSize


        val notification = repository.getNotifications(perPage, ((pageNumber -1) * perPage))

        val nextKey = if(notification.isEmpty()){null}else{pageNumber + 1}
        val prevKey = if (pageNumber == 1){null}else{pageNumber + 1}

        return LoadResult.Page(data = notification, prevKey = prevKey, nextKey = nextKey)
    }
}