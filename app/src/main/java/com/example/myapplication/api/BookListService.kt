package com.example.myapplication.api

import com.example.myapplication.models.PopularMovie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BookListService {
    @GET("3/movie/popular")
    suspend fun popularBookList(@Query("api_key")apikey:String): Response<PopularMovie>

}