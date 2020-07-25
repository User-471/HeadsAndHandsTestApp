package com.headsandhandstestapp.utils

import android.util.Patterns

fun String.isEmailValid() = Patterns.EMAIL_ADDRESS.matcher(this).matches()