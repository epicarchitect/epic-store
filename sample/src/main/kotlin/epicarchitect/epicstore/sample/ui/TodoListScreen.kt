package epicarchitect.epicstore.sample.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import epicarchitect.epicstore.compose.rememberEpicStoreEntry
import epicarchitect.epicstore.sample.di.DI
import epicarchitect.epicstore.sample.feature.TodoFeature
import epicarchitect.epicstore.sample.feature.TodoIdsFeature

@Composable
fun TodoListScreen(
    onAddButtonClick: () -> Unit,
    onTaskClick: (taskId: Int) -> Unit
) {
    val todoIdsFeature = rememberEpicStoreEntry { DI.createTodoIdsFeature() }
    val todoIdsState by todoIdsFeature.state.collectAsState()

    when (val state = todoIdsState) {
        is TodoIdsFeature.State.Loaded -> {
            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = onAddButtonClick,
                        content = {
                            Icon(
                                imageVector = Icons.Default.Add,
                                tint = Color.White,
                                contentDescription = null
                            )
                        }
                    )
                }
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    items(state.todoIds) { id ->
                        TodoItem(
                            todoId = id,
                            onClick = { onTaskClick(id) }
                        )
                    }
                }
            }
        }
        is TodoIdsFeature.State.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun LazyItemScope.TodoItem(
    todoId: Int,
    onClick: () -> Unit
) {
    val todoFeature = rememberEpicStoreEntry { DI.createTodoFeature(todoId) }

    val todoState by todoFeature.state.collectAsState()

    when (val state = todoState) {
        is TodoFeature.State.Loaded -> {
            val todo = state.todo
            val toggleCompletionFeature = rememberEpicStoreEntry {
                DI.createTodoToggleCompletionFeature(todoId)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onClick)
                    .padding(8.dp)
                    .animateItemPlacement(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = todo.completed,
                    onCheckedChange = {
                        toggleCompletionFeature.toggleCompletion()
                    }
                )

                Column {
                    Text(
                        text = todo.title,
                        style = MaterialTheme.typography.h6
                    )

                    Text(
                        text = todo.content,
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }
        is TodoFeature.State.Loading -> {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth().height(44.dp)
            )
        }
        is TodoFeature.State.NotFound -> {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text("Not found")
            }
        }
    }
}