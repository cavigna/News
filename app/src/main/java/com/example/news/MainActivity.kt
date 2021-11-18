package com.example.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.viewbinding.BuildConfig
import com.example.news.application.NewsApp
import com.example.news.databinding.ActivityMainBinding
import com.example.news.viewmodel.NewsModelFactory
import com.example.news.viewmodel.NewsViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel  by viewModels<NewsViewModel> {
        NewsModelFactory((application as NewsApp).repositorio)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        //viewModel.traerUltimasNoticiasAr()


        setContentView(binding.root)




    }
}