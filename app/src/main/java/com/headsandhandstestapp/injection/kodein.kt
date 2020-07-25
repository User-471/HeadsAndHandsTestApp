package com.headsandhandstestapp.injection

import android.app.Application
import com.headsandhandstestapp.injection.module.appModule
import com.headsandhandstestapp.injection.module.netModule
import org.kodein.di.Kodein

lateinit var di: Kodein

fun initKodein(app: Application) {

    di = Kodein {
        import(appModule(app))
        import(netModule())
    }
}