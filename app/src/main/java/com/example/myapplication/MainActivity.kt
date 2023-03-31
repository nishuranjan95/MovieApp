package com.example.myapplication

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.fragments.SecondFragment
import com.example.myapplication.fragments.StartFragment
import com.example.myapplication.interfaces.OnMovieItemClick
import com.example.myapplication.network.Network
import com.example.myapplication.paging.MoviePagingAdapter
import com.example.myapplication.repository.MovieRepository
import com.example.myapplication.viewmodels.MainViewModel
import com.example.myapplication.viewmodels.MainViewModelFactory

class MainActivity : AppCompatActivity(),OnMovieItemClick {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var pagingAdapter:MoviePagingAdapter
    private lateinit var movieRepository:MovieRepository
    private lateinit var localBroadcastManager: LocalBroadcastManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        //networkChangeReceiver= NetworkChangeReceiver()
        localBroadcastManager=LocalBroadcastManager.getInstance(this)
//        val filter = IntentFilter("com.example.myapplication.NETWORK")
//        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
//        registerReceiver(networkChangeReceiver, filter)

//        val filter = IntentFilter("com.example.myapplication.NETWORK")
//        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
//        localBroadcastManager.registerReceiver(apiCallReceiver, filter)
        if(!Network.isConnected(applicationContext)) {
            Toast.makeText(applicationContext,"Please turn on network",Toast.LENGTH_SHORT).show()

        } else {
         initialize()
        }
        val fragment= StartFragment()
        binding.button.setOnClickListener{
            supportFragmentManager.beginTransaction().replace(binding.framelayout.id,fragment,fragment::class.java.name)
                .addToBackStack(null)
                .commit()
        }

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
    private fun initialize(){
        movieRepository=(application as MyApplication).movieRepository
        mainViewModel = ViewModelProvider(
            this,
            MainViewModelFactory(movieRepository)
        )[MainViewModel::class.java]
        binding.recyclerview.layoutManager = GridLayoutManager(this, 3)
        pagingAdapter = MoviePagingAdapter(this, this)
        binding.recyclerview.adapter = pagingAdapter
        observer(this)
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(apiCallReceiver)

    }
    private val apiCallReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == "com.example.myapplication.NETWORK") {
                initialize()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter("com.example.myapplication.NETWORK")
        LocalBroadcastManager.getInstance(this).registerReceiver(apiCallReceiver, filter)
    }

}