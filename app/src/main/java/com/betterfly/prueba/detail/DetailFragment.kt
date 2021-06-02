package com.betterfly.prueba.detail

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.betterfly.prueba.R
import com.betterfly.prueba.common.entities.PeliculaEntity
import com.betterfly.prueba.databinding.FragmentDetailBinding
import com.betterfly.prueba.detail.viewModel.DetailViewModel
import com.betterfly.prueba.main.MainActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class DetailFragment : Fragment() {
    private lateinit var mBinding: FragmentDetailBinding
    private lateinit var mDetailViewModel: DetailViewModel
    private lateinit var mPeliculaEntity: PeliculaEntity
    private var mActivity: MainActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDetailViewModel = ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        mBinding = FragmentDetailBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
    }

    private fun setupViewModel() {
        mDetailViewModel.peliculaSelected.observe(viewLifecycleOwner, {
            mPeliculaEntity = it
            setUiStore(it)

            setupActionBar()
        })
    }

    private fun setupActionBar() {
        mActivity = activity as? MainActivity
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                mActivity?.onBackPressed()
                true
            }
            R.id.cerrarSesion -> {
                mActivity?.cerrarSession()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setUiStore(peliculaEntity: PeliculaEntity) {
        with(mBinding){
            title.text = peliculaEntity.title
            overview.text = peliculaEntity.overview
            Glide.with(requireContext())
                .load("https://image.tmdb.org/t/p/w200" + peliculaEntity.poster_path)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerInside()
                .into(imagen)
        }
    }

    override fun onDestroy() {
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mActivity?.supportActionBar?.title = getString(R.string.app_name)

        setHasOptionsMenu(false)
        super.onDestroy()
    }
}