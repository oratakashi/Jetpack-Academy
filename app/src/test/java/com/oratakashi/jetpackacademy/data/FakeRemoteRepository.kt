package com.oratakashi.jetpackacademy.data

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.oratakashi.jetpackacademy.BuildConfig
import com.oratakashi.jetpackacademy.core.Config
import com.oratakashi.jetpackacademy.data.model.movie.ResponseMovie
import com.oratakashi.jetpackacademy.data.model.tv.ResponseTv
import com.oratakashi.jetpackacademy.data.network.ApiEndpoint
import io.reactivex.Single
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class FakeRemoteRepository {
    private fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = when (BuildConfig.DEBUG) {
                true -> HttpLoggingInterceptor.Level.BODY
                false -> HttpLoggingInterceptor.Level.NONE
            }
        }
    }
    private fun providesApiKey() : Interceptor = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var request: Request = chain.request()
            val url: HttpUrl = request.url.newBuilder()
                .addQueryParameter("api_key", Config.key)
                .build()
            request = request.newBuilder().url(url).build()
            return chain.proceed(request)
        }
    }
    private fun providesHttpClient(
        interceptor: HttpLoggingInterceptor,
        apiKey: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            retryOnConnectionFailure(true)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
            addInterceptor(interceptor)
            addInterceptor(apiKey)
        }.build()
    }
    private fun getRetrofit() : Retrofit = Retrofit.Builder().apply {
        baseUrl(BuildConfig.BASE_URL)
        client(providesHttpClient(
            providesHttpLoggingInterceptor(),
            providesApiKey()
        ))
        addConverterFactory(GsonConverterFactory.create())
        addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
    }.build()

    fun getMovie() : Single<ResponseMovie> =
        getRetrofit().create(ApiEndpoint::class.java).getMovie(1)

    fun searchMovie(keyword : String) : Single<ResponseMovie> =
        getRetrofit().create(ApiEndpoint::class.java).searchMovie(keyword, 1)

    fun getTv() : Single<ResponseTv> =
        getRetrofit().create(ApiEndpoint::class.java).getTv(1)

    fun searchTv(keyword: String) : Single<ResponseTv> =
        getRetrofit().create(ApiEndpoint::class.java).searchTv(keyword, 1)
}