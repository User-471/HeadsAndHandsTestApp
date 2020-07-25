package com.headsandhandstestapp.injection.module

import com.headsandhandstestapp.api.inteceptors.LoggingInterceptor
import com.headsandhandstestapp.api.service.WeatherService
import com.headsandhandstestapp.utils.BASE_URL
import okhttp3.OkHttpClient
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun netModule() = Kodein.Module {
    bind<OkHttpClient>() with singleton { provideOkHttpClient() }
    bind<Retrofit>("1") with singleton { provideRestRetrofit(instance()) }
    bind<WeatherService>() with singleton { instance<Retrofit>("1").create(WeatherService::class.java) }
}

private fun provideOkHttpClient(): OkHttpClient =
    OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(LoggingInterceptor())
        .build()

private fun provideRestRetrofit(client: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()