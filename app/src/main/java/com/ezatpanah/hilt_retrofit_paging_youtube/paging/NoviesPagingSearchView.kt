package com.ezatpanah.hilt_retrofit_paging_youtube.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ezatpanah.hilt_retrofit_paging_youtube.repository.ApiRepository
import com.ezatpanah.hilt_retrofit_paging_youtube.response.MoviesListResponse
import retrofit2.HttpException
import retrofit2.http.Query

class NoviesPagingSearchView (
    private val repository: ApiRepository,
    private val query: String
) : PagingSource<Int, MoviesListResponse.Result>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviesListResponse.Result> {
        return try {

            val currentPage = params.key ?: 1
            val response = repository.searchMovieList(currentPage, query )
            val data = response.body()!!.results
            val responseData = mutableListOf<MoviesListResponse.Result>()
            responseData.addAll(data)

            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }



    }


    override fun getRefreshKey(state: PagingState<Int, MoviesListResponse.Result>): Int? {
        return null
    }
}