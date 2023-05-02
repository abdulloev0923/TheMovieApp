package com.ezatpanah.hilt_retrofit_paging_youtube.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.ezatpanah.hilt_retrofit_paging_youtube.paging.*
import com.ezatpanah.hilt_retrofit_paging_youtube.repository.ApiRepository
import com.ezatpanah.hilt_retrofit_paging_youtube.response.MovieDetailsResponse
import com.ezatpanah.hilt_retrofit_paging_youtube.response.MoviesListResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.http.Query
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val repository: ApiRepository) : ViewModel() {

    val loading = MutableLiveData<Boolean>()

    val moviesList = Pager(PagingConfig(1)) {
        MoviesPagingSource(repository)
    }.flow.cachedIn(viewModelScope)

    val nowPlayingList = Pager(PagingConfig(1)) {
        NowPlayingMoviePagingSource(repository)
    }.flow.cachedIn(viewModelScope)
    val TopRatedList = Pager(PagingConfig(1)) {
        TopRatedMoviePagingSource(repository)
    }.flow.cachedIn(viewModelScope)
    val Upcoming = Pager(PagingConfig(1)) {
        UpcomingMoviePagingSource(repository)
    }.flow.cachedIn(viewModelScope)

    fun getSearch(query: String):Flow<PagingData<MoviesListResponse.Result>> {
        val searchMovie = Pager(PagingConfig(1)) {
            NoviesPagingSearchView(repository,
            query)
        }.flow
        return searchMovie
    }


    //Api
    val detailsMovie = MutableLiveData<MovieDetailsResponse>()
    fun loadDetailsMovie(id: Int) = viewModelScope.launch {
        loading.postValue(true)
        val response = repository.getMovieDetails(id)
        if (response.isSuccessful) {
            detailsMovie.postValue(response.body())
        }
        loading.postValue(false)
    }



}