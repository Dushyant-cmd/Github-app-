package com.kaisebhi.githubproject.utils

import android.app.Application
import com.kaisebhi.githubproject.data.network.ApiInterface
import com.kaisebhi.githubproject.data.network.RetrofitClient
import com.kaisebhi.githubproject.data.repository.MainRepository
import com.kaisebhi.githubproject.data.room.DatabaseRoom

class ApplicationClass: Application() {
    lateinit var mainRepo: MainRepository
    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        val apiInterface = RetrofitClient.getRetrofitClient().create(ApiInterface::class.java)
        val database = DatabaseRoom.getDatabase(applicationContext)
        mainRepo = MainRepository(applicationContext, apiInterface, database)
    }
}