package com.example.androiddevchallenge

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * Custom Application class to provide Global application context for the project.
 *
 * @author Lin Guo
 * @since 2021/2/26
 */
class GlobalApp : Application() {

    companion object {
        /**
         * Global application context.
         */
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

}