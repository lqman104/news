package com.luqman.news.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.luqman.news.core.model.Resource
import com.luqman.news.data.model.News

class NewsPagingDataSource(
    private val dataSource: NewsDataSource,
) : PagingSource<Int, News>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News> {
        return try {
            val page = (params.key ?: 1)
            when (val response = dataSource.getNews(page)) {
                is Resource.Success -> {
                    val list = response.data.orEmpty()
                    // the index 0 until 2 already show on headline section
                    val returnData = if (page == 1 && 3 < list.size) {
                        list.subList(3, list.size)
                    } else {
                        list
                    }

                    LoadResult.Page(
                        data = returnData,
                        prevKey = null,
                        nextKey = if (list.isEmpty()) null else page + 1
                    )
                }

                is Resource.Error -> {
                    LoadResult.Error(response.exception ?: NullPointerException())
                }

                else -> {
                    LoadResult.Page(
                        data = listOf(),
                        prevKey = null,
                        nextKey = if (response.data?.isEmpty() == true) null else page + 1
                    )
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, News>): Int? {
        return state.anchorPosition
    }
}