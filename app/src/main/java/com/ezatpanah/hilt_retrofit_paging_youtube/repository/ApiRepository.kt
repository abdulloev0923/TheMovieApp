package com.ezatpanah.hilt_retrofit_paging_youtube.repository

import com.ezatpanah.hilt_retrofit_paging_youtube.api.ApiServices
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val apiServices: ApiServices,
) {
    suspend fun getPopularMoviesList(page: Int) = apiServices.getPopularMoviesList(page)
    suspend fun getNowPlayingMoviesList(page: Int) = apiServices.getNowPlayingMoviesList(page)
    suspend fun getTopRatedMoviesList(page: Int) = apiServices.getTopRatedMoviesList(page)
    suspend fun getUpcomingMoviesList(page: Int) = apiServices.getUpcomingMoviesList(page)
    suspend fun getMovieDetails(id: Int) = apiServices.getMovieDetails(id)
    suspend fun searchMovieList(page: Int, query : String) = apiServices.searchMovie(page, query)
}