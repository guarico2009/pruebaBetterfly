package com.betterfly.prueba.main.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.betterfly.prueba.common.entities.PeliculaEntity
import com.betterfly.prueba.main.model.MainInteractor
import com.bumptech.glide.load.engine.bitmap_recycle.IntegerArrayAdapter

class MainViewModel: ViewModel() {
    private var peliculaList: MutableList<PeliculaEntity>
    private var interactor: MainInteractor
    private var page = 1

    init {
        peliculaList = mutableListOf()
        interactor = MainInteractor()
    }

    private val peliculas: MutableLiveData<MutableList<PeliculaEntity>> by lazy {
        MutableLiveData<MutableList<PeliculaEntity>>().also {
            loadPeliculas(page)
        }
    }

    fun getPeliculas(numPage: Int): LiveData<MutableList<PeliculaEntity>> {
        page = numPage
        return peliculas
    }

    fun cargarPeliculas(numPage: Int) {
        page = numPage
        loadPeliculas(page)
    }

    private fun loadPeliculas(page: Int){
        interactor.getPeliculas(page) {
            peliculas.value = it
            peliculaList = it
        }
    }
}