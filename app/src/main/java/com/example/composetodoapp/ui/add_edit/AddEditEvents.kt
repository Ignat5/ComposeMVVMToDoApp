package com.example.composetodoapp.ui.add_edit

sealed class AddEditEvents {
    data class onTitleChange (val title: String): AddEditEvents()
    data class onDescriptionChange (val description: String): AddEditEvents()
    object onConfirmToDoClick: AddEditEvents()
}
