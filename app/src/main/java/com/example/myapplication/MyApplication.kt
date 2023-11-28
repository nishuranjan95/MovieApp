package com.example.myapplication

import android.app.Application
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.myapplication.api.MovieListService
import com.example.myapplication.api.RetrofitHelper
import com.example.myapplication.repository.MovieRepository
import com.example.myapplication.viewmodels.MainViewModel
import com.example.myapplication.viewmodels.MainViewModelFactory
import dagger.hilt.android.HiltAndroidApp
import kotlin.text.Typography.dagger


class MyApplication: Application() {
    lateinit var movieRepository:MovieRepository
    lateinit var mainViewModel: MainViewModel
    companion object {
        lateinit var instance: MyApplication
            private set
    }
    override fun onCreate() {
        super.onCreate()
        instance=this
        initialize()
    }
    private fun initialize(){
        val movieListService = RetrofitHelper.getAuthInstance().create(MovieListService::class.java)
        movieRepository = MovieRepository(movieListService)
    }

}