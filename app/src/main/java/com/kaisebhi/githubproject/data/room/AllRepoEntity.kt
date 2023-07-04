package com.kaisebhi.githubproject.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repoTable")
data class AllRepoEntity(
    @PrimaryKey(autoGenerate = true)
    val uniqueId: Int,
    val name: String?,
    val html_url: String?,
    val description: String?,
    val message: String?) {
}