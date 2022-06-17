package com.example.composetodoapp.ui.all_todos

import com.example.composetodoapp.data.room.ToDoEntity

sealed class AllToDoEvents {
    data class onDeleteToDoClick(val todo: ToDoEntity): AllToDoEvents()
    data class onDoneClick(val todo: ToDoEntity, val isDone: Boolean): AllToDoEvents()
    object onUndoClick: AllToDoEvents()
    data class onToDoClick(val todo: ToDoEntity): AllToDoEvents()
    object onAddToDoClick: AllToDoEvents()
}
