package com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.databinding.ActivityUserBinding
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.adapter.UserAdapter
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.viewmodel.UserViewModel
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.networking.BaseRepository
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.networking.FakeAbstract
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.util.AutoUpdatableAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class UserActivity : AppCompatActivity(){

    private lateinit var binding: ActivityUserBinding
    private val viewModel: UserViewModel by viewModels()
    private var adapter: UserAdapter?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAdapter()
        viewObservers()
    }

    private fun setAdapter() {
        adapter = UserAdapter()
        binding.rcvContainer.adapter = adapter
    }

    private fun viewObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.listUser.collect{ uiState ->
                    when(uiState){
                        is UserViewModel.UsersUiState.Success -> {
                            if(uiState.news.isNotEmpty()) {
                                binding.progressBar.isVisible = false
                                adapter?.submitList(uiState.news)
                            }
                        }
                        is UserViewModel.UsersUiState.Error -> {
                            binding.progressBar.isVisible = false
                            Toast.makeText(this@UserActivity, uiState.error, Toast.LENGTH_SHORT).show()
                        }
                        is UserViewModel.UsersUiState.Exception -> {
                            binding.progressBar.isVisible = false
                            Toast.makeText(this@UserActivity, uiState.exception, Toast.LENGTH_SHORT).show()
                        }
                        is UserViewModel.UsersUiState.Loading -> {
                            binding.progressBar.isVisible = true
                        }
                    }
                }
            }
        }

        binding.edtSearch.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if(!s.isNullOrEmpty())
                    adapter?.filter(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
                //
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                //
            }
        })
    }
}