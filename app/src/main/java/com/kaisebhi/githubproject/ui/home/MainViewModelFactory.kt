package com.kaisebhi.githubproject.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Database
import com.kaisebhi.githubproject.data.network.ApiInterface
import com.kaisebhi.githubproject.data.repository.MainRepository

class MainViewModelFactory(private val repo: MainRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repo) as T
    }
}