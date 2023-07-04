package com.kaisebhi.githubproject.data.repository

import android.content.Context
import android.os.Build.VERSION_CODES.P
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.kaisebhi.githubproject.data.network.AddData
import com.kaisebhi.githubproject.data.network.ApiInterface
import com.kaisebhi.githubproject.data.room.AllRepoEntity
import com.kaisebhi.githubproject.data.room.DatabaseRoom
import com.kaisebhi.githubproject.utils.NetworkUtils
import com.kaisebhi.githubproject.utils.ResponseHandler
import org.json.JSONObject
import retrofit2.Response

class MainRepository(private val ctx: Context, private val apiInterface: ApiInterface, private val db: DatabaseRoom) {
    val mutableLiveData: MutableLiveData<ResponseHandler<List<AllRepoEntity>>> = MutableLiveData()
    val TAG = "MainRepo.kt"

    suspend fun getRepos(owner: String) {
        if(NetworkUtils.getNetworkState(ctx)) {
            //get data from api and add it to room
            try {
                val res: Response<List<AllRepoEntity>> = apiInterface.getAllRepo(owner)
                if(res.isSuccessful) {
                    res.body()?.let {
                        mutableLiveData.value = ResponseHandler.Success(it)
                        db.getAllRepoDao().deleteList();
                        db.getAllRepoDao().insertList(it)
                    } ?: run {
                        mutableLiveData.value = ResponseHandler.Error("Network problem")
                    }
                }
            } catch (e: Exception) {
                mutableLiveData.value = ResponseHandler.Error("$e")
                Log.d(TAG, "getRepos: $e")
            }
        } else {
            //get data from room
            try {
                mutableLiveData.value = ResponseHandler.Success(db.getAllRepoDao().query())
            } catch (e: Exception) {
                mutableLiveData.value = ResponseHandler.Error("Local db error")
            }
        }
    }

    suspend fun addRepo(ownerName: String, jsonParams: AddData) {
        if(NetworkUtils.getNetworkState(ctx)) {
            try {
                val res: Response<AllRepoEntity> = apiInterface.addRepo(ownerName, jsonParams)
                if(res.isSuccessful) {
                    res.body()?.let {
                        if(!it.message.isNullOrEmpty()) {
                            Toast.makeText(ctx, it.message, Toast.LENGTH_SHORT).show()
                        }
                        mutableLiveData.value?.let {
                            mutableLiveData.value = it
                        }?: run {
                            mutableLiveData.value = ResponseHandler.Success(listOf(it))
                        }
                        db.getAllRepoDao().insert(it)
                    } ?: run {
                        Toast.makeText(ctx, "Error in Api", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch(e: Exception) {
                Toast.makeText(ctx, "$e", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "addRepo: $e")
            }
        } else {
            Toast.makeText(ctx, "Turn on Internet", Toast.LENGTH_SHORT).show()
        }
    }
}