package com.example.myapplication.interceptor

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response


class MyInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url: HttpUrl = request.url.newBuilder().addQueryParameter("api_key", "5f3e92a58253101f7f1a2cd537afbb94").build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)

//        val request=chain.request().newBuilder()
//        request.addQ("api_key","5f3e92a58253101f7f1a2cd537afbb94")
//        return chain.proceed(request.build())
    }
}