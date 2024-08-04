package com.stellar.data.sources

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.stellar.data.Repository
import com.stellar.data.db.entetities.ProductEntity
import com.stellar.data.types.FavoriteProductWithProduct
import com.stellar.data.types.Product

/*
@OptIn(ExperimentalPagingApi::class)
class SearchProductsMediator(
    private val repository: Repository,
    val searchQuery : String
) : RemoteMediator<Int, ProductEntity>(){



    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ProductEntity>
    ): MediatorResult {

        return try {

            val pageNumber : Int = when(loadType){
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {

                    val lastItem = state.lastItemOrNull()
                    if(lastItem == null){
                        1
                    }else{
                        val currentPageSize = state.config.pageSize
                        val totalLoadedItems = state.pages.sumOf { it.data.size }
                        (totalLoadedItems / currentPageSize) + 1
                    }


                }
            }


            val perPage = state.pages.size
            val products = repository.getSearchProducts(searchString = searchQuery  ,page = pageNumber, perPage = perPage)
            if(loadType == LoadType.REFRESH){
                repository.clearAllProducts()
            }
            repository.updateDbWithProducts(products)
            return MediatorResult.Success(
                endOfPaginationReached = products.isEmpty()
            )

        }catch (e : Exception){
            MediatorResult.Error(e)
        }





    }
}*/











@OptIn(ExperimentalPagingApi::class)
class SearchProductSource(
    private val repository: Repository,
    val searchQuery : String
) : PagingSource<Int, FavoriteProductWithProduct>(){



    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FavoriteProductWithProduct> {

        return try {

            val pageNumber = params.key ?: 1
            val perPage = params.loadSize


            val response = repository.getSearchProducts(searchQuery, pageNumber,perPage )

            val nextKey = if(response.isEmpty()){null}else{pageNumber + 1}
            val prevKey = if(pageNumber == 1){null}else{pageNumber - 1}

            return LoadResult.Page(data = response, prevKey = prevKey, nextKey = nextKey)

        }catch (e : Exception)
        {
            LoadResult.Error(e)
        }


    }

    override fun getRefreshKey(state: PagingState<Int, FavoriteProductWithProduct>): Int? {
        return null
    }


}



