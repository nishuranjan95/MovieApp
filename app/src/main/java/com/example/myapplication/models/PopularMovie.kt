package com.example.myapplication.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class PopularMovie(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)

