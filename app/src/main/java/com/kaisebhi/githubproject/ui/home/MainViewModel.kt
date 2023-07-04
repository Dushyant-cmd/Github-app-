package com.kaisebhi.githubproject.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Database
import com.kaisebhi.githubproject.data.network.AddData
import com.kaisebhi.githubproject.data.network.ApiInterface
import com.kaisebhi.githubproject.data.repository.MainRepository
import com.kaisebhi.githubproject.data.room.AllRepoDao
import com.kaisebhi.githubproject.data.room.AllRepoEntity
import com.kaisebhi.githubproject.utils.ResponseHandler
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainViewModel(private val repo: MainRepository): ViewModel() {
    val mutableLiveData: MutableLiveData<ResponseHandler<List<AllRepoEntity>>> = MutableLiveData()
    val liveData: LiveData<ResponseHandler<List<AllRepoEntity>>>
    get() = mutableLiveData

    /**Below method to get all repo */
    fun getRepo(owner: String) {
        viewModelScope.launch {
            repo.getRepos(owner)
            mutableLiveData.value = repo.mutableLiveData.value
        }
    }

    /**Below method to add repo */
    fun addRepo(owner: String, repoName: String) {
        viewModelScope.launch {
            repo.addRepo(owner, AddData(repoName,
                "This is description which is added by testing with android",
            false,
            "public"))
            mutableLiveData.value = repo.mutableLiveData.value
        }
    }
}