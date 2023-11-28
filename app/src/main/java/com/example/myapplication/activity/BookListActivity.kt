package com.example.myapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityBookListBinding

class BookListActivity : AppCompatActivity() {

    private lateinit var binding:ActivityBookListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityBookListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    
}