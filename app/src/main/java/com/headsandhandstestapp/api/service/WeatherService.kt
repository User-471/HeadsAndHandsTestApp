package com.headsandhandstestapp.api.service

import com.headsandhandstestapp.model.WeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WeatherService {

    @Headers(value = ["Accept: application/json", "Content-type:application/json"])
    @GET("/current")
    fun getCityWeather(
        @Query("access_key") accessKey: String,
        @Query("query") query: String
    ): Single<WeatherResponse>
}