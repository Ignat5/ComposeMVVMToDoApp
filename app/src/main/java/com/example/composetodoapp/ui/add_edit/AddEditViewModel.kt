package com.example.composetodoapp.ui.add_edit

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetodoapp.data.repository.ToDoRepository
import com.example.composetodoapp.data.room.ToDoEntity
import com.example.composetodoapp.util.UIEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val repository: ToDoRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var todo = mutableStateOf<ToDoEntity?>(null)
        private set

    var title = mutableStateOf("")
        private set

    var description = mutableStateOf("")
        private set

    private val _uiEvents = Channel<UIEvents>()
    val uiEvents = _uiEvents.receiveAsFlow()

    init {
        val todoId = savedStateHandle.get<Int>("todoId")
        todoId?.let { id ->
            if (id != -1)
                viewModelScope.launch {
                    repository.getTodoById(id)?.let { currentToDo ->
                        todo.value = currentToDo
                        title.value = currentToDo.title
                        description.value = currentToDo.description ?: ""
                    }
                }
        }
    }

    fun onEvent(event: AddEditEvents) {
        when (event) {
            is AddEditEvents.onConfirmToDoClick -> {
                viewModelScope.launch {
                    if (title.value.isNotBlank()) {
                        repository.insertToDo(
                            ToDoEntity(
                                title.value,
                                description.value.ifBlank { null },
                                todo.value?.isDone ?: false,
                                todo.value?.id ?: 0
                            )
                        )
                        _uiEvents.send(UIEvents.PopBackStack)

                    } else
                        _uiEvents.send(
                            UIEvents.ShowSnackBar(
                                "Title can't be empty",
                                null
                            )
                        )
                }
            }
            is AddEditEvents.onTitleChange -> {
                title.value = event.title
            }
            is AddEditEvents.onDescriptionChange -> {
                description.value = event.description
            }
        }
    }

}