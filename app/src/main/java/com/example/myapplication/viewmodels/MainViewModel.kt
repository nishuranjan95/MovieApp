package com.example.myapplication.viewmodels

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.*
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.myapplication.DummyWorker
import com.example.myapplication.MyApplication
import com.example.myapplication.models.PopularMovie
import com.example.myapplication.models.Result
import com.example.myapplication.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val movieRepository: MovieRepository):ViewModel() {

    //private lateinit var context:Context;
    private val workStatusLiveData = MutableLiveData<WorkInfo>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getPagingMovies()
        }
    }
    val popularMovieData:LiveData<PopularMovie>
    get() = movieRepository.popularMovieLiveData

    var pagingMovieList=movieRepository.getPagingMovies().cachedIn(viewModelScope)

    private val apiCallReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == "com.example.myapplication.API_CALL_INTENT") {
                pagingMovieList
            }
        }
    }
//    init {
//        val intentFilter = IntentFilter("com.example.myapplication.API_CALL_INTENT")
//        LocalBroadcastManager.getInstance(MyApplication.instance).registerReceiver(apiCallReceiver, intentFilter)
//    }
//    override fun onCleared() {
//        super.onCleared()
//        LocalBroadcastManager.getInstance(MyApplication.instance).unregisterReceiver(apiCallReceiver)
//    }

    fun loadDataFromWork(lifecycleOwner: LifecycleOwner,context: Context){
        val constraint=androidx.work.Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val networkRequest= OneTimeWorkRequest
            .Builder(DummyWorker::class.java)
            .setConstraints(constraint)//i added constraints
            .build()
        WorkManager.getInstance(context).getWorkInfoByIdLiveData(networkRequest.id)
            .observe(lifecycleOwner) {

            }

    }


}