package com.example.myapplication.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.api.MovieListService
import com.example.myapplication.models.PopularMovie

class MovieRepository(private var movieListService: MovieListService) {
      private val popularMutableLiveData=MutableLiveData<PopularMovie>()

    val popularMovieLiveData:LiveData<PopularMovie>
    get()=popularMutableLiveData

    suspend fun getPopularMovie(apiKey:String){
        val result=movieListService.popularMovieList(apiKey)
        if(result?.body()!=null){
            popularMutableLiveData.postValue(result.body())
        }
    }

}