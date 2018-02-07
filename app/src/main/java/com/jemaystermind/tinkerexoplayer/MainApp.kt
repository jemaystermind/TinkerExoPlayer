package com.jemaystermind.tinkerexoplayer

import android.app.Application
import com.facebook.stetho.Stetho

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}
