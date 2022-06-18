package com.example.composetodoapp.ui.add_edit.compose

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composetodoapp.ui.add_edit.AddEditEvents
import com.example.composetodoapp.ui.add_edit.AddEditViewModel
import com.example.composetodoapp.util.UIEvents
import kotlinx.coroutines.flow.collect

@Composable
fun AddEditScreen(
    onPopBackStack: () -> Unit,
    viewModel: AddEditViewModel = hiltViewModel()
) {

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                is UIEvents.PopBackStack -> onPopBackStack()
                is UIEvents.ShowSnackBar ->
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action,
                        duration = SnackbarDuration.Short
                )
                else -> Unit
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 16.dp)
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            TextField(value = viewModel.title.value, onValueChange = {
                viewModel.onEvent(
                    AddEditEvents.onTitleChange(it)
                )
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            TextField(value = viewModel.description.value, onValueChange = {
                viewModel.onEvent(
                    AddEditEvents.onDescriptionChange(it)
                )
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            Button(onClick = {
                viewModel.onEvent(
                    AddEditEvents.onConfirmToDoClick
                )
            }, modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
            ) {
                Text(text = "save", fontSize = 24.sp)
            }
        }
    }
}