package com.uc.week4retrofit.view

import CompanyAdapter
import GenreAdapter
import LanguageAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.uc.week4retrofit.databinding.ActivityMovieDetailBinding
import com.uc.week4retrofit.helper.Const
import com.uc.week4retrofit.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetail : AppCompatActivity() {
    private  lateinit var binding: ActivityMovieDetailBinding
    private lateinit var nowPlayingViewModel: MoviesViewModel
    private lateinit var genreadapter: GenreAdapter
    private lateinit var comadapter: CompanyAdapter
    private lateinit var langadapter: LanguageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val movieId= intent.getIntExtra("movie_id", 0)
        Toast.makeText(applicationContext, "Movie ID : ${movieId}", Toast.LENGTH_SHORT).show()

        nowPlayingViewModel = ViewModelProvider(this)[MoviesViewModel::class.java]
        nowPlayingViewModel.getMovieDetails(Const.API_KEY, movieId)
        nowPlayingViewModel.movieDetails.observe(this, Observer {
            response ->
            binding.tvMovieDetailPlaying.apply {
                text = response.title
            }
            binding.tvOverview.apply {
                text = response.overview
            }
            binding.rvGenre.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            genreadapter = GenreAdapter(response.genres)
            binding.rvGenre.adapter = genreadapter

            binding.rvCompany.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            comadapter = CompanyAdapter(response.production_companies)
            binding.rvCompany.adapter = comadapter

            binding.rvLanguage.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            langadapter = LanguageAdapter(response.spoken_languages)
            binding.rvLanguage.adapter = langadapter

            Glide.with(applicationContext).load(Const.IMG_URL + response.backdrop_path)
                .into(binding.backropPath)
        })
    }
}