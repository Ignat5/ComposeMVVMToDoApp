package com.example.composetodoapp.ui.all_todos.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composetodoapp.ui.all_todos.AllToDoEvents
import com.example.composetodoapp.ui.all_todos.AllToDoViewModel
import com.example.composetodoapp.util.UIEvents
import kotlinx.coroutines.flow.collect

@Composable
fun AllToDoScreen(
    onNavigate: (UIEvents.Navigate) -> Unit,
    viewModel: AllToDoViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()

    val todos = viewModel.allTodoList.collectAsState(initial = emptyList())
    LaunchedEffect(key1 = true) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                is UIEvents.ShowSnackBar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action,
                        duration = SnackbarDuration.Long
                    )
                    if (result == SnackbarResult.ActionPerformed)
                        viewModel.onEvent(AllToDoEvents.onUndoClick)
                }
                is UIEvents.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(AllToDoEvents.onAddToDoClick)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(todos.value) { index, todo ->
                ToDoItem(todo = todo, onEvent = viewModel::onEvent, modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        viewModel.onEvent(AllToDoEvents.onToDoClick(todo))
                    }
                    .padding(16.dp)
                )
            }
        }
    }

}