package com.example.myapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.PopularMovie
import com.example.myapplication.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val movieRepository: MovieRepository):ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getPopularMovie("5f3e92a58253101f7f1a2cd537afbb94")
        }
    }
    val popularMovieData:LiveData<PopularMovie>
    get() = movieRepository.popularMovieLiveData
}