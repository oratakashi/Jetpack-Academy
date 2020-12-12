package com.oratakashi.jetpackacademy.data.network

import com.oratakashi.jetpackacademy.data.model.movie.ResponseMovie
import com.oratakashi.jetpackacademy.data.model.tv.ResponseTv
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiEndpoint {
    @GET("movie/upcoming")
    fun getMovie(
        @Query("page") page : Int
    ) : Single<ResponseMovie>

    @GET("search/movie")
    fun searchMovie(
        @Query("query") query: String,
        @Query("page") page : Int
    ) : Single<ResponseMovie>

    @GET("tv/airing_today")
    fun getTv(
        @Query("page") page : Int
    ) : Single<ResponseTv>

    @GET("search/tv")
    fun searchTv(
        @Query("query") query: String,
        @Query("page") page : Int
    ) : Single<ResponseTv>
}