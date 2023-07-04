package com.kaisebhi.githubproject.data.room

import androidx.room.*

@Dao
interface AllRepoDao {
    @Insert
    suspend fun insert(allRepoEntity: AllRepoEntity)

    @Insert
    suspend fun insertList(allRepoEntity: List<AllRepoEntity>)

    @Query("SELECT * FROM repoTable")
    suspend fun query(): List<AllRepoEntity>

    @Delete
    suspend fun delete(allRepoEntity: AllRepoEntity)

    @Query("DELETE FROM repoTable")
    suspend fun deleteList()

    @Update
    suspend fun update(allRepoEntity: AllRepoEntity)
}