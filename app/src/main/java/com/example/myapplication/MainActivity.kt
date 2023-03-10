package com.example.myapplication

import com.example.myapplication.adapter.MovieAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.api.MovieListService
import com.example.myapplication.api.RetrofitHelper
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.interfaces.OnMovieItemClick
import com.example.myapplication.paging.MoviePagingAdapter
import com.example.myapplication.repository.MovieRepository
import com.example.myapplication.viewmodels.MainViewModel
import com.example.myapplication.viewmodels.MainViewModelFactory

class MainActivity : AppCompatActivity(),OnMovieItemClick {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var pagingAdapter:MoviePagingAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)

        val movieListService=RetrofitHelper.getInstance().create(MovieListService::class.java)
        val movieRepository=MovieRepository(movieListService)
        mainViewModel= ViewModelProvider(this,MainViewModelFactory(movieRepository))[MainViewModel::class.java]
        binding.recyclerview.layoutManager=GridLayoutManager(this,3)
        pagingAdapter=MoviePagingAdapter(this,this)
        binding.recyclerview.adapter=pagingAdapter
        observer(this)

    }
    private fun observer(context: Context){
        mainViewModel.pagingMovieList.observe(this) {
            pagingAdapter.submitData(lifecycle,it)

        }
    }

    override fun onClick(movie: com.example.myapplication.models.Result) {
        val intent=Intent(applicationContext,MovieDetailsActivity::class.java)
        intent.putExtra("movie",movie.overview)
        startActivity(intent)
    }

}