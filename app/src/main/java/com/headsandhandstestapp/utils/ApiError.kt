package com.headsandhandstestapp.utils

import com.google.gson.JsonParser
import retrofit2.HttpException

class ApiError constructor(error: Throwable) {
    var message = "Произошла ошибка"

    init {
        if (error is HttpException) {
            val errorJsonString = error.response()
                .errorBody()?.string()
            try {
                this.message = JsonParser().parse(errorJsonString)
                    .asJsonObject["message"]
                    .asString
            } catch (_: Exception) {
                this.message = "Произошла ошибка"
            }
        } else {
            this.message = error.message ?: this.message
        }
    }
}