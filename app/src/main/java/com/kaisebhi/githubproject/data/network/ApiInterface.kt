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