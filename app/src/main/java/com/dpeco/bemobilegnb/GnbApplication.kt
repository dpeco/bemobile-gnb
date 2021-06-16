package com.dpeco.bemobilegnb

import android.app.Application
import com.dpeco.bemobilegnb.dagger.*

/**
 * Created by dpeco
 * Main Application, used to inject with dagger
 */

class GnbApplication : Application() {

    lateinit var appComponent: GnbComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerGnbComponent.builder()
            .retrofitModule(RetrofitModule())
            .serviceModule(ServiceModule())
            .useCaseModule(UseCaseModule())
            .build()
    }
}