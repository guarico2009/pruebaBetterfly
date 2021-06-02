package com.betterfly.prueba

import android.app.Application
import com.betterfly.prueba.common.api.RestAPI
import com.google.firebase.auth.FirebaseAuth

class PruebaApplication : Application() {
    companion object{
        lateinit var restAPI: RestAPI
        lateinit var auth: FirebaseAuth
    }

    override fun onCreate() {
        super.onCreate()
        auth = FirebaseAuth.getInstance()
        restAPI = RestAPI.getInstance(this)
    }
}