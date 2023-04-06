package com.example.myapplication

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.myapplication.network.Network
import com.example.myapplication.viewmodels.MainViewModel

class DummyWorker(private val context: Context,private val params: WorkerParameters): Worker(context,params) {

    override fun doWork(): Result {

        if(Network.isConnected(context)) {
            val repository=(context as MyApplication).movieRepository
            val pagingMovies = repository.getPagingMovies()

            val viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(context)
                .create(MainViewModel::class.java)
            viewModel.pagingMovieList=pagingMovies
            //val outputData=workDataOf(Pair("livedata",pagingMovies))
            return Result.success()
        }
        return Result.failure()
    }
}