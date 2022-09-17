package epicarchitect.epicstore.sample.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
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

@Composable
fun TodoListScreen(
    onAddButtonClick: () -> Unit,
    onTaskClick: (taskId: Int) -> Unit
) {
    val todoIdsFeature = rememberEpicStoreEntry { DI.createTodoIdsFeature() }
    val todoIds by todoIdsFeature.state.collectAsState()

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
            items(todoIds) { id ->
                TodoItem(
                    todoId = id,
                    onClick = { onTaskClick(id) }
                )
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
    val todoToggleCompletionFeature =
        rememberEpicStoreEntry { DI.createTodoToggleCompletionFeature(todoId) }
    val todo by todoFeature.state.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(8.dp)
            .animateItemPlacement(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = todo?.completed ?: false,
            onCheckedChange = {
                todoToggleCompletionFeature.toggle()
            }
        )

        Column {
            Text(
                text = todo?.title ?: "not found",
                style = MaterialTheme.typography.h6
            )

            Text(
                text = todo?.content ?: "not found",
                style = MaterialTheme.typography.body1
            )
        }
    }
}