package com.oratakashi.jetpackacademy.data.network

import com.oratakashi.jetpackacademy.data.model.movie.ResponseMovie
import com.oratakashi.jetpackacademy.data.model.tv.ResponseTv
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiEndpoint {
    @GET("movie/upcoming")
    fun getMovie() : Single<ResponseMovie>

    @GET("movie/upcoming")
    fun getMovieLimit(
        @Query("page") page : Int
    ) : Single<ResponseMovie>

    @GET("search/movie")
    fun searchMovie(
        @Query("query") query: String
    ) : Single<ResponseMovie>

    @GET("search/movie")
    fun searchMovieLimit(
        @Query("query") query: String,
        @Query("page") page : Int
    ) : Single<ResponseMovie>

    @GET("tv/airing_today")
    fun getTv() : Single<ResponseTv>

    @GET("tv/airing_today")
    fun getTvLimit(
        @Query("page") page : Int
    ) : Single<ResponseTv>

    @GET("search/tv")
    fun searchTv(
        @Query("query") query: String
    ) : Single<ResponseTv>

    @GET("search/tv")
    fun searchTvLimit(
        @Query("query") query: String,
        @Query("page") page : Int
    ) : Single<ResponseTv>
}