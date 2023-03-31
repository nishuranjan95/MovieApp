package com.example.myapplication.viewmodels

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.paging.cachedIn
import com.example.myapplication.MyApplication
import com.example.myapplication.models.PopularMovie
import com.example.myapplication.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val movieRepository: MovieRepository):ViewModel() {

    //private lateinit var context:Context;

    init {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getPopularMovie("5f3e92a58253101f7f1a2cd537afbb94")
        }
    }
    val popularMovieData:LiveData<PopularMovie>
    get() = movieRepository.popularMovieLiveData

    val pagingMovieList=movieRepository.getPagingMovies().cachedIn(viewModelScope)

    private val apiCallReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == "com.example.myapplication.API_CALL_INTENT") {
                pagingMovieList
            }
        }
    }
    init {
        val intentFilter = IntentFilter("com.example.myapplication.API_CALL_INTENT")
        LocalBroadcastManager.getInstance(MyApplication.instance).registerReceiver(apiCallReceiver, intentFilter)
    }
    override fun onCleared() {
        super.onCleared()
        LocalBroadcastManager.getInstance(MyApplication.instance).unregisterReceiver(apiCallReceiver)
    }

}