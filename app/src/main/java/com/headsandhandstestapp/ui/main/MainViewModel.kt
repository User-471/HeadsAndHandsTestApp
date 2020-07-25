package com.headsandhandstestapp.ui.main

import androidx.lifecycle.MutableLiveData
import com.headsandhandstestapp.api.service.WeatherService
import com.headsandhandstestapp.model.WeatherResponse
import com.headsandhandstestapp.ui.base.BaseViewModel
import com.headsandhandstestapp.utils.ApiError
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.kodein.di.generic.instance

class MainViewModel: BaseViewModel<MainViewModel>() {

    val weatherService: WeatherService by instance<WeatherService>()

    val errorMessageData = MutableLiveData<String>()
    val loadingLiveData = MutableLiveData<Boolean>()

    fun getCityWeather(
        accessKey: String,
        query: String
    ): MutableLiveData<WeatherResponse> {

        val data = MutableLiveData<WeatherResponse>()

        weatherService.getCityWeather(accessKey, query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                loadingLiveData.value = true
            }
            .doAfterSuccess { loadingLiveData.value = false }
            .subscribeBy(onSuccess = {
                data.value = it
            }, onError = {
                val errorMessage = ApiError(it).message
                errorMessageData.value = errorMessage
            })

        return data
    }

}