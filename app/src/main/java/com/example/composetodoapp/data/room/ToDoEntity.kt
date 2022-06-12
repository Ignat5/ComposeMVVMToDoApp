package com.example.composetodoapp.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ToDoEntity(
    val title: String,
    val description: String?,
    val isDone: Boolean,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
