package com.betterfly.prueba.login.model

import android.util.Log
import com.betterfly.prueba.PruebaApplication
import com.betterfly.prueba.main.MainActivity

class LoginInteractor {
    fun login(email: String, password: String, callback: (Boolean) -> Unit){
        PruebaApplication.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    callback(true)
                } else {
                    callback(false)
                }
            }

    }

    fun create(email: String, password: String, callback: (Boolean) -> Unit) {
        PruebaApplication.auth.createUserWithEmailAndPassword(email, password)
             .addOnCompleteListener() { task ->
                 if (task.isSuccessful) {
                     callback(true)
                 } else {
                     callback(false)
                 }
             }
    }
}