package com.example.myapplication.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.myapplication.api.MovieListService
import com.example.myapplication.models.PopularMovie
import com.example.myapplication.paging.MoviePagingSource

class MovieRepository(private var movieListService: MovieListService) {
      private val popularMutableLiveData=MutableLiveData<PopularMovie>()
//      private val pagingPopularMutableLiveData=MutableLiveData<PagingData<com.example.myapplication.models.Result>>()
//
//    val pagingPopularMovieLiveData:LiveData<PagingData<com.example.myapplication.models.Result>>
//    get()=pagingPopularMutableLiveData


    val popularMovieLiveData:LiveData<PopularMovie>
    get()=popularMutableLiveData

    suspend fun getPopularMovie(apiKey:String){
        val result=movieListService.popularMovieList(apiKey)
        if(result.body() !=null){
            popularMutableLiveData.postValue(result.body())
        }
    }
    fun getPagingMovies() = Pager(
        config = PagingConfig(pageSize=20,maxSize=100),
        pagingSourceFactory = { MoviePagingSource(movieListService) }
    ).liveData


}