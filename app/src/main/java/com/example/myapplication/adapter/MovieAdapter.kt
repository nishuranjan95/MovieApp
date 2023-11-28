package com.example.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.SingleitemBinding
import com.example.myapplication.interfaces.OnMovieItemClick

class MovieAdapter(private val movies: List<com.example.myapplication.models.Result>, private val context: Context,private val onMovieItemClick: OnMovieItemClick) : RecyclerView.Adapter<MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SingleitemBinding.inflate(inflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
           holder.bind(movies,position,context,onMovieItemClick)
    }

}

class MovieViewHolder(private val binding:SingleitemBinding) : RecyclerView.ViewHolder(binding.root){
      fun bind(movies: List<com.example.myapplication.models.Result>,position: Int,context: Context,onMovieItemClick: OnMovieItemClick){
          binding.title.text= movies[position].title
          Glide.with(context)
              .load("http://image.tmdb.org/t/p/w185"+movies[position].poster_path)
              .placeholder(R.drawable.photo)
              .into(binding.image)

          binding.singleParent.setOnClickListener{
              onMovieItemClick.onClick(movies[position])
          }
      }
}
