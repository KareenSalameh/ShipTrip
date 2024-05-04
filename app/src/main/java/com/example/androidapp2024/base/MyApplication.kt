package com.example.androidapp2024.base

import android.app.Application
import android.content.Context
import com.example.androidapp2024.R

class MyApplication: Application() {
    object Globals{
        var appContext: Context?= null
    }

    override fun onCreate() {
        super.onCreate()
        Globals.appContext = applicationContext
    }


}