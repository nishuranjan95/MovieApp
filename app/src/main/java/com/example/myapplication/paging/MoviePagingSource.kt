package com.example.myapplication.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.myapplication.api.MovieListService
import com.example.myapplication.models.Result
import com.example.myapplication.repository.MovieRepository

class MoviePagingSource(private val movieListService: MovieListService): PagingSource<Int, Result>() {
    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {

        return try{
            val pos=params.key?:1
            val response=movieListService.popularMovieList2("5f3e92a58253101f7f1a2cd537afbb94",pos)
            val prevKey=if(pos==1) null else pos-1
            val nextKey= if(pos == response.body()?.total_pages) null else (pos + 1)
            LoadResult.Page(response.body()!!.results,prevKey,nextKey)
        } catch (e:java.lang.Exception){
            LoadResult.Error(e)
        }

    }
}