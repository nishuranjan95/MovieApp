package com.example.myapplication

import MovieAdapter
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.api.MovieListService
import com.example.myapplication.api.RetrofitHelper
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.repository.MovieRepository
import com.example.myapplication.viewmodels.MainViewModel
import com.example.myapplication.viewmodels.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)

        val movieListService=RetrofitHelper.getInstance().create(MovieListService::class.java)
        val movieRepository=MovieRepository(movieListService)
        mainViewModel= ViewModelProvider(this,MainViewModelFactory(movieRepository))[MainViewModel::class.java]
        binding.recyclerview.layoutManager=GridLayoutManager(this,3)
        observer(this)

    }
    private fun observer(context: Context){
        mainViewModel.popularMovieData.observe(this) {
            binding.recyclerview.adapter = MovieAdapter(it.results,context)
        }
    }

}