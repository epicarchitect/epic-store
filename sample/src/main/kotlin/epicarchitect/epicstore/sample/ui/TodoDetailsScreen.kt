package epicarchitect.epicstore.sample.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import epicarchitect.epicstore.compose.rememberEpicStoreEntry
import epicarchitect.epicstore.sample.di.DI
import epicarchitect.epicstore.sample.feature.TodoFeature

@Composable
fun TodoDetailsScreen(
    todoId: Int,
    onEditClick: () -> Unit
) {
    val todoFeature = rememberEpicStoreEntry { DI.createTodoFeature(todoId) }
    val todoToggleCompletionFeature = rememberEpicStoreEntry {
        DI.createTodoToggleCompletionFeature(todoId)
    }

    val todoState by todoFeature.state.collectAsState()

    when (val state = todoState) {
        is TodoFeature.State.Loaded -> {
            val todo = state.todo
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = todo.title,
                        style = MaterialTheme.typography.h6
                    )

                    Text(
                        text = todo.content,
                        style = MaterialTheme.typography.body1
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { todoToggleCompletionFeature.toggleCompletion() },
                        content = {
                            if (todo.completed) {
                                Text("Completed")
                            } else {
                                Text("Complete")
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = if (todo.completed) {
                                MaterialTheme.colors.surface
                            } else {
                                MaterialTheme.colors.primary
                            },
                            contentColor = if (todo.completed) {
                                MaterialTheme.colors.onSurface
                            } else {
                                MaterialTheme.colors.onPrimary
                            }
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = onEditClick,
                        content = {
                            Text("Edit")
                        }
                    )
                }
            }
        }
        is TodoFeature.State.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is TodoFeature.State.NotFound -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Not found")
            }
        }
    }
}