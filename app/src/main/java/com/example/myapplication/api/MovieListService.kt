package com.example.myapplication.api

import com.example.myapplication.models.PopularMovie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieListService {

    @GET("3/movie/popular")
    suspend fun popularMovieList():Response<PopularMovie>

    @GET("3/movie/popular")
    suspend fun popularMovieList2(@Query("page")page:Int):Response<PopularMovie>

}