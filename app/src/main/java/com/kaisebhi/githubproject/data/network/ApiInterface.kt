package com.kaisebhi.githubproject.data.network

import com.kaisebhi.githubproject.data.room.AllRepoEntity
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {
    @GET("orgs/{owner}/repos")
    suspend fun getAllRepo(@Path("owner") ownerName: String): Response<List<AllRepoEntity>>

    @POST("orgs/{owner}/repos")
    @Headers("Authorization: Bearer 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")
    suspend fun addRepo(@Path("owner") ownerName: String, @Body addData: AddData): Response<AllRepoEntity>
}