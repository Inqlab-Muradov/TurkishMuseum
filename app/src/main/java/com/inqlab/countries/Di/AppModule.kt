package com.inqlab.countries.Di

import android.content.Context
import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.inqlab.countries.api.ApiKeyInterceptor
import com.inqlab.countries.api.CityService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiKeyInterceptor():ApiKeyInterceptor{
        return ApiKeyInterceptor()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor() : HttpLoggingInterceptor{
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @Singleton
    fun provideChuckers(@ApplicationContext context : Context) : ChuckerInterceptor{
        return ChuckerInterceptor(context)
    }

    @Provides
    @Singleton
    fun provideOkHttp(
        apiKeyInterceptor: ApiKeyInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        chuckerInterceptor: ChuckerInterceptor  ) : OkHttpClient{
        return OkHttpClient.Builder().addInterceptor(apiKeyInterceptor)
            .addNetworkInterceptor(httpLoggingInterceptor)
            .addInterceptor(chuckerInterceptor)
            .readTimeout(60,TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun cityProvide(client: OkHttpClient) : CityService{
        return Retrofit.Builder().baseUrl("https://www.nosyapi.com/apiv2/service/")
            .addConverterFactory(GsonConverterFactory.create()).client(client)
            .build()
            .create(CityService::class.java)
    }


}