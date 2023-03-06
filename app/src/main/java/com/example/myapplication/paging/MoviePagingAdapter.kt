package com.example.myapplication.paging

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.SingleitemBinding
import com.example.myapplication.interfaces.OnMovieItemClick
import com.example.myapplication.models.Result

class MoviePagingAdapter(private val context: Context, private val onMovieItemClick: OnMovieItemClick): PagingDataAdapter<Result,MoviePagingAdapter.PagingViewHolder>(MovieCallback) {

    class PagingViewHolder(private val binding: SingleitemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(result: Result, context: Context, onMovieItemClick: OnMovieItemClick){
            binding.title.text=result.title
            Glide.with(context)
                .load("http://image.tmdb.org/t/p/w185"+result.poster_path)
                .placeholder(R.drawable.photo)
                .into(binding.image)

            binding.singleParent.setOnClickListener{
                onMovieItemClick.onClick(result)
            }
        }
    }

    override fun onBindViewHolder(holder: PagingViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, context, onMovieItemClick)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SingleitemBinding.inflate(inflater, parent, false)
        return PagingViewHolder(binding)
    }

    object MovieCallback : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }
}