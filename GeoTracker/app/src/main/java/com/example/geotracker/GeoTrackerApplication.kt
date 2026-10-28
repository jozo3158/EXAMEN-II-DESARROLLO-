package com.example.geotracker

import android.app.Application
import com.example.geotracker.data.TokenManager

class GeoTrackerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        TokenManager.init(applicationContext)
    }
}
