package com.betterfly.prueba.detail.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.betterfly.prueba.common.entities.PeliculaEntity

class DetailViewModel: ViewModel() {
    val peliculaSelected = MutableLiveData<PeliculaEntity>()

    fun setPeliculaSelected(peliculaEntity: PeliculaEntity){
        peliculaSelected.value = peliculaEntity
    }

    fun getPeliculaSelected(): LiveData<PeliculaEntity> {
        return peliculaSelected
    }
}