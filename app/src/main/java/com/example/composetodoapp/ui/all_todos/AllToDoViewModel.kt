package com.example.composetodoapp.ui.all_todos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetodoapp.data.repository.ToDoRepository
import com.example.composetodoapp.data.room.ToDoEntity
import com.example.composetodoapp.util.Routes
import com.example.composetodoapp.util.UIEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllToDoViewModel @Inject constructor(
    private val repository: ToDoRepository
): ViewModel() {

    val allTodoList = repository.getAllTodos()

    private val _uiEvents = Channel<UIEvents>()
    val uiEvents = _uiEvents.receiveAsFlow()

    private var lastDeletedToDo: ToDoEntity? = null

    fun onEvent(event: AllToDoEvents) {
        when (event) {
            is AllToDoEvents.onAddToDoClick -> {
                sendUIEvent(
                    UIEvents.Navigate(Routes.ADD_EDIT_TODO_ROUTE)
                )
            }
            is AllToDoEvents.onToDoClick -> {
                sendUIEvent(
                    UIEvents.Navigate(
                        Routes.ADD_EDIT_TODO_ROUTE + "?todoId=${event.todo.id}"
                    )
                )
            }
            is AllToDoEvents.onDoneClick -> {
                val updatedTodo = event.todo.copy(isDone = event.isDone)
                viewModelScope.launch {
                    repository.insertToDo(updatedTodo)
                }
            }
            is AllToDoEvents.onUndoClick -> {
                viewModelScope.launch {
                    lastDeletedToDo?.let {
                        repository.insertToDo(it)
                    }
                }
            }
            is AllToDoEvents.onDeleteToDoClick -> {
                viewModelScope.launch {
                    repository.deleteTodo(event.todo)
                    lastDeletedToDo = event.todo
                    sendUIEvent(
                        UIEvents.ShowSnackBar(
                            message = "ToDo is deleted",
                            action = "Undo deletion"
                        )
                    )
                }
            }
        }
    }

    private fun sendUIEvent(event: UIEvents) =
        viewModelScope.launch {
            _uiEvents.send(event)
        }

}