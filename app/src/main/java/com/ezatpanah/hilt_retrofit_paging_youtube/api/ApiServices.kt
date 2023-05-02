package com.ezatpanah.hilt_retrofit_paging_youtube.api

import com.ezatpanah.hilt_retrofit_paging_youtube.response.Genre
import com.ezatpanah.hilt_retrofit_paging_youtube.response.MovieDetailsResponse
import com.ezatpanah.hilt_retrofit_paging_youtube.response.MoviesListResponse
import com.ezatpanah.hilt_retrofit_paging_youtube.response.getActors
import com.ezatpanah.hilt_retrofit_paging_youtube.utils.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    //    https://api.themoviedb.org/3/movie/550?api_key=***
    //    https://api.themoviedb.org/3/movie/popular?api_key=***
    //    https://api.themoviedb.org/3/

    @GET("movie/popular")
    suspend fun getPopularMoviesList(@Query("page") page: Int): Response<MoviesListResponse>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMoviesList(@Query("page") page: Int): Response<MoviesListResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMoviesList(@Query("page") page: Int): Response<MoviesListResponse>
    @GET("movie/upcoming")
    suspend fun getUpcomingMoviesList(@Query("page") page: Int): Response<MoviesListResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") id: Int): Response<MovieDetailsResponse>

    @GET("movie/{movie_id}/credits?")
    suspend fun getActors(
        @Path("movie_id") movieId: Int,
        @Query("api_key") api_key: String = API_KEY
    ): getActors

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("page")page: Int,
        @Query("query") query: String,
        @Query("api_key") api_key: String = API_KEY

    ): Response<MoviesListResponse>

    @GET("genre/movie/list")
    suspend fun getGenre(
        @Query("api_key")api_key: String = API_KEY
    ): Genre



}