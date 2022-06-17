package com.example.composetodoapp.util

sealed class UIEvents {
    object PopBackStack: UIEvents()
    data class Navigate(val route: String): UIEvents()
    data class ShowSnackBar(
        val message: String,
        val action: String? = null
    ): UIEvents()
}
