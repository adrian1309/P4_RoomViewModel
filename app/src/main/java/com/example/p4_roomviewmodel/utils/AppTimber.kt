package com.example.p4_roomviewmodel.utils

import android.app.Application
import timber.log.Timber
import timber.log.Timber.*


class AppTimber : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(DebugTree())
    }
}