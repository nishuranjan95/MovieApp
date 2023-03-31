package com.example.myapplication

import android.app.Application
import com.example.myapplication.api.MovieListService
import com.example.myapplication.api.RetrofitHelper
import com.example.myapplication.repository.MovieRepository

class MyApplication: Application() {
    lateinit var movieRepository:MovieRepository
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
        val movieListService = RetrofitHelper.getInstance().create(MovieListService::class.java)
        movieRepository = MovieRepository(movieListService)
    }
}