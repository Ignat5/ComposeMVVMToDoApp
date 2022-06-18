package com.example.composetodoapp.ui.all_todos.compose

import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composetodoapp.data.room.ToDoEntity
import com.example.composetodoapp.ui.all_todos.AllToDoEvents

@Composable
fun ToDoItem(
    todo: ToDoEntity,
    onEvent: (AllToDoEvents) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = todo.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(end = 8.dp),
                        color = Color.Black,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    IconButton(onClick = {
                        onEvent(AllToDoEvents.onDeleteToDoClick(todo))
                    }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "delete")
                    }
                }
                Spacer(modifier = Modifier
                    .height(8.dp))
                Text(
                    text = todo.description ?: "",
                    color = Color.Black,
                    fontSize = 14.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Checkbox(checked = todo.isDone, onCheckedChange = { isChecked ->
            onEvent(AllToDoEvents.onDoneClick(todo, isChecked))
        })
    }
}