package com.example.myapplication.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.activity.BookListActivity
import com.example.myapplication.databinding.FragmentStartBinding


class StartFragment : Fragment() {

    private lateinit var binding:FragmentStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.text.setOnClickListener{
           requireActivity().supportFragmentManager
               .beginTransaction().replace(binding.container2.id,MapsFragment())
               .addToBackStack(null)
                .commit()
        }

        binding.camera.setOnClickListener{
            childFragmentManager
                .beginTransaction().replace(binding.container2.id,CameraFragment())
                .addToBackStack(null)
                .commit()
        }
        binding.book.setOnClickListener{
            startActivity(Intent(activity,BookListActivity::class.java))
        }
    }

}