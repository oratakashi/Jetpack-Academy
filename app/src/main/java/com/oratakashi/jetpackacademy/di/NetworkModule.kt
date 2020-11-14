package com.oratakashi.jetpackacademy.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.oratakashi.jetpackacademy.BuildConfig
import com.oratakashi.jetpackacademy.core.Config
import com.oratakashi.jetpackacademy.data.network.ApiEndpoint
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = when (BuildConfig.DEBUG) {
                true -> HttpLoggingInterceptor.Level.BODY
                false -> HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Provides
    @Singleton
    fun providesChucker(@ApplicationContext context: Context): ChuckerInterceptor {
        return ChuckerInterceptor(context)
    }

    @Provides
    @Singleton
    fun providesApiKey() : Interceptor = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var request: Request = chain.request()
            val url: HttpUrl = request.url.newBuilder()
                .addQueryParameter("api_key", Config.key)
                .build()
            request = request.newBuilder().url(url).build()
            return chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    fun providesHttpClient(
        interceptor: HttpLoggingInterceptor,
        chucker: ChuckerInterceptor,
        apiKey: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            retryOnConnectionFailure(true)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
            addInterceptor(interceptor)
            if(BuildConfig.DEBUG) addInterceptor(chucker)
            addInterceptor(apiKey)
        }.build()
    }

    @Provides
    @Singleton
    fun providesHttpAdapter(client: OkHttpClient): Retrofit {
        return Retrofit.Builder().apply {
            client(client)
            baseUrl(BuildConfig.BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
            addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
        }.build()
    }

    @Provides
    @Singleton
    fun providesApiEndPoint(retrofit: Retrofit) : ApiEndpoint {
        return retrofit.create(ApiEndpoint::class.java)
    }
}