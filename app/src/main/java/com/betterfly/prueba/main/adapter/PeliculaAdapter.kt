package com.betterfly.prueba.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.betterfly.prueba.R
import com.betterfly.prueba.common.entities.PeliculaEntity
import com.betterfly.prueba.databinding.ItemListBinding
import com.betterfly.prueba.main.MainActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


class PeliculaAdapter(private var peliculas: MutableList<PeliculaEntity>, private val listener: OnClickListener): RecyclerView.Adapter<PeliculaAdapter.ViewHolder>() {
    private lateinit var context: Context
    private val LOADING = 0
    private val ITEM = 1
    private val isLoadingAdded = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pelicula = peliculas.get(position)
        var cropText = pelicula.overview

        if(pelicula.overview.length > 140){
            cropText = pelicula.overview.substring(0, 140) + "..."
        }

        when (getItemViewType(position)) {
            ITEM -> {
                with(holder) {
                    setListener(pelicula)
                    binding.titulo.text = pelicula.title
                    binding.descripcion.text = cropText
                    Glide.with(context)
                            .load("https://image.tmdb.org/t/p/w200" + pelicula.poster_path)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .centerInside()
                            .into(binding.imagen)
                }
            }
        }


    }

    override fun getItemCount(): Int = peliculas.size

    override fun getItemViewType(position: Int): Int {
        return if (position == peliculas.size - 1 && isLoadingAdded) LOADING else ITEM
    }

    fun setPeliculas(peliculas: MutableList<PeliculaEntity>) {
        this.peliculas.addAll(peliculas)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = ItemListBinding.bind(view)

        fun setListener(user: PeliculaEntity){
            binding.root.setOnClickListener { listener.onClick(user) }
        }
    }
}