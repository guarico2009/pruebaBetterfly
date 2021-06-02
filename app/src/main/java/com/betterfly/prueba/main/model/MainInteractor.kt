package com.betterfly.prueba.main.model

import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.betterfly.prueba.PruebaApplication
import com.betterfly.prueba.common.entities.PeliculaEntity
import com.betterfly.prueba.common.utils.Constants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainInteractor {

    fun getPeliculas(page: Int, callback: (MutableList<PeliculaEntity>) -> Unit){
        val url = Constants.API_URL + Constants.API_VERSION + Constants.GET_MOVIES_PATH + "?api_key=" + Constants.API_KEY + "&page=" + page

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            val jsonList = response.getJSONArray("results").toString()
            val mutableListType = object : TypeToken<MutableList<PeliculaEntity>>(){}.type
            val peliculaList = Gson().fromJson<MutableList<PeliculaEntity>>(jsonList, mutableListType)

            callback(peliculaList)
        },{
            it.printStackTrace()
        })

        PruebaApplication.restAPI.addToRequestQueue(jsonObjectRequest)
    }
}