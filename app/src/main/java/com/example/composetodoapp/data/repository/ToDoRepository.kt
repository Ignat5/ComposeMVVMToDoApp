package com.example.composetodoapp.data.repository

import com.example.composetodoapp.data.room.ToDoEntity
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {

    suspend fun insertToDo(toDoEntity: ToDoEntity)

    suspend fun getTodoById(id: Int): ToDoEntity?

    suspend fun deleteTodoById(toDoEntity: ToDoEntity)

    fun getAllTodos(): Flow<List<ToDoEntity>>

}