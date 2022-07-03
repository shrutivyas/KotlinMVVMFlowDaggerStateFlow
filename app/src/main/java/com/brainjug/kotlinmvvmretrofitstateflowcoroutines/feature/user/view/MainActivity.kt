package com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.view

import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.R
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.databinding.ActivityMainBinding
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.e("## onCreate")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewObservers()
    }

    private fun viewObservers() {
        /*viewModel.listUser.observe(this){
            Timber.e("## it : ${it.size} ${it[0].name}")
        }*/
    }


}