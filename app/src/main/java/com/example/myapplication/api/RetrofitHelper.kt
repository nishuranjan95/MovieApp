package com.example.myapplication.api

import com.example.myapplication.interceptor.MyInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    private const val BASE_URL="https://api.themoviedb.org/"
    private const val BOOK_URL="http://localhost:8080/"
    private var myInterceptor=MyInterceptor()

    fun getInstance():Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    fun getBookInstance():Retrofit{
        return Retrofit.Builder()
            .baseUrl(BOOK_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    fun getAuthInstance():Retrofit{
        return Retrofit.Builder()
            .client(provideOkHttpClient(myInterceptor))
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private fun provideOkHttpClient(myInterceptor: MyInterceptor):OkHttpClient{
        return OkHttpClient.Builder().addInterceptor(myInterceptor).build()
    }
}