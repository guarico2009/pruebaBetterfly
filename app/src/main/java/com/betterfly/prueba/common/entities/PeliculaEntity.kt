package com.betterfly.prueba.common.entities

data class PeliculaEntity (var id: Long, var title: String, var overview: String, var poster_path: String) {
    constructor() : this(0,"","", "")
}