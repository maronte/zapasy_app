package com.example.Zapasy

import android.app.Application

class ZapazyApp: Application() {
    companion object{
        lateinit var INSTANCE: Application
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}