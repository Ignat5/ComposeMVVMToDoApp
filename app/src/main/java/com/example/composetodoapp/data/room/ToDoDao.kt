package com.example.composetodoapp.data.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToDo(toDoEntity: ToDoEntity)

    @Query("SELECT * FROM todoentity WHERE id=:id")
    suspend fun getTodoById(id: Int): ToDoEntity?

    @Delete
    suspend fun deleteTodoById(toDoEntity: ToDoEntity)

    @Query("SELECT * FROM todoentity")
    fun getAllTodos(): Flow<List<ToDoEntity>>
}