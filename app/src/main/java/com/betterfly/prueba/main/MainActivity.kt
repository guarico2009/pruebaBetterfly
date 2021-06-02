package com.betterfly.prueba.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.betterfly.prueba.PruebaApplication
import com.betterfly.prueba.R
import com.betterfly.prueba.common.entities.PeliculaEntity
import com.betterfly.prueba.databinding.ActivityMainBinding
import com.betterfly.prueba.detail.DetailFragment
import com.betterfly.prueba.detail.viewModel.DetailViewModel
import com.betterfly.prueba.login.LoginActivity
import com.betterfly.prueba.main.adapter.OnClickListener
import com.betterfly.prueba.main.adapter.PeliculaAdapter
import com.betterfly.prueba.main.viewModel.MainViewModel


class MainActivity : AppCompatActivity(), OnClickListener {
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mAdapter: PeliculaAdapter
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager
    private lateinit var mMainViewModel: MainViewModel
    private lateinit var mDetailViewModel: DetailViewModel
    private var isLoading: Boolean = false
    private var numPage: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setupViewModel()
        setupRecylcerView()
    }

    private fun setupViewModel() {
        mMainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mMainViewModel.getPeliculas(numPage).observe(this, { peliculas ->
            mAdapter.setPeliculas(peliculas)
            mBinding.progressBar.visibility = if (peliculas.size == 0) View.VISIBLE else View.GONE
        })

        mDetailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
    }

    private fun launchEditFragment(peliculaEntity: PeliculaEntity = PeliculaEntity()) {
        mDetailViewModel.setPeliculaSelected(peliculaEntity)

        val fragment = DetailFragment()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.add(R.id.containerMain, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun setupRecylcerView() {
        mAdapter = PeliculaAdapter(mutableListOf(), this)
        linearLayoutManager = LinearLayoutManager(this)

        mBinding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = mAdapter
        }

        mBinding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = recyclerView.layoutManager!!.itemCount
                if (!isLoading && totalItemCount == (linearLayoutManager as LinearLayoutManager).findLastVisibleItemPosition() + 1) {
                    numPage += 1
                    mMainViewModel.cargarPeliculas(numPage)
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.cerrarSesion -> {
                cerrarSession()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onClick(peliculaEntity: PeliculaEntity) {
        launchEditFragment(peliculaEntity)
    }

    fun cerrarSession() {
        PruebaApplication.auth.signOut()

        Toast.makeText(this, "Su session ha expirado.", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}