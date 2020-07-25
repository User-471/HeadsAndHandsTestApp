package com.headsandhandstestapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.headsandhandstestapp.injection.initKodein
import com.headsandhandstestapp.injection.module.appModule
import com.headsandhandstestapp.injection.module.netModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

class HeadsAndHandsTestApp : Application(), KodeinAware {

    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    override val kodein: Kodein = Kodein.lazy {
        import(appModule(this@HeadsAndHandsTestApp))
        import(netModule())
    }

    override fun onCreate() {
        super.onCreate()

        initKodein(this)
    }
}