package com.example.composetodoapp.data.repository

import com.example.composetodoapp.data.room.ToDoDao
import com.example.composetodoapp.data.room.ToDoEntity
import kotlinx.coroutines.flow.Flow

class ToDoRepositoryImpl(
    private val dao: ToDoDao
): ToDoRepository {

    override suspend fun insertToDo(toDoEntity: ToDoEntity) {
        dao.insertToDo(toDoEntity)
    }

    override suspend fun getTodoById(id: Int): ToDoEntity? = dao.getTodoById(id)

    override fun getAllTodos(): Flow<List<ToDoEntity>> = dao.getAllTodos()

    override suspend fun deleteTodoById(toDoEntity: ToDoEntity) {
        dao.deleteTodoById(toDoEntity)
    }
}