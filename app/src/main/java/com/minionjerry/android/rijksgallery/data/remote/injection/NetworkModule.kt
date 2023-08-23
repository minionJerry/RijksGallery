package com.minionjerry.android.rijksgallery.data.remote.injection

import com.minionjerry.android.rijksgallery.BuildConfig
import com.minionjerry.android.rijksgallery.data.remote.networking.ArtObjectService
import com.minionjerry.android.rijksgallery.data.repository.source.paging.MAX_PAGE_SIZE
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://www.rijksmuseum.nl/api/"
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
     fun apiKeyAsQuery(chain: Interceptor.Chain) = chain.proceed(
        chain.request()
            .newBuilder()
            .url(chain.request().url.newBuilder()
                .addQueryParameter("ps",MAX_PAGE_SIZE.toString())
                .addQueryParameter("key", BuildConfig.API_KEY).build())
            .build()
    )

    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient
            .Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .addInterceptor {apiKeyAsQuery(chain = it) }
            .build()

    @Provides
    fun provideMoshi(): Moshi =
        Moshi
            .Builder()
            .add(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory())
            .build()

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    @Provides
    fun provideArtObjectService(retrofit: Retrofit): ArtObjectService =
        retrofit.create(ArtObjectService::class.java)
}