package com.example.myapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.myapplication.databinding.ActivityMainBinding
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
        localBroadcastManager=LocalBroadcastManager.getInstance(applicationContext)
//        val intent=Intent(CONNECTIVITY_ACTION)
//        localBroadcastManager.sendBroadcast(intent)
        //setWorker()
        if (!Network.isConnected(applicationContext)) {
            Toast.makeText(applicationContext, "Please turn on network", Toast.LENGTH_SHORT).show()

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

    override fun onDestroy() {
        super.onDestroy()
        //LocalBroadcastManager.getInstance(this).unregisterReceiver(apiCallReceiver)

    }
    private val apiCallReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == "android.net.conn.CONNECTIVITY_CHANGE") {
                Log.i("networkcall2",intent.action.toString())
                if(Network.isConnected(context)) initialize()
                else{
                    Toast.makeText(context,"turn on network",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onResume() {
       // localBroadcastManager.registerReceiver(apiCallReceiver,IntentFilter(CONNECTIVITY_SERVICE))
        super.onResume()
        setWorker()
    }
    private fun setWorker() {
        val constraint=androidx.work.Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val networkRequest= OneTimeWorkRequest
            .Builder(DummyWorker::class.java)
            .setConstraints(constraint)//i added constraints
            .build()
        WorkManager.getInstance(this).enqueue(networkRequest)
    }
}