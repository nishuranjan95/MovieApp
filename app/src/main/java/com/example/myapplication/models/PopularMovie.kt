package com.example.myapplication.models

import androidx.room.ColumnInfo
import androidx.room.Entity


data class PopularMovie(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)

