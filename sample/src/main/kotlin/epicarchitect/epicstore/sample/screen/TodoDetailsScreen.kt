package epicarchitect.epicstore.sample.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import epicarchitect.epicstore.compose.rememberEpicStoreEntry
import epicarchitect.epicstore.sample.di.DI

@Composable
fun TodoDetailsScreen(todoId: Int) {
    val todoFeature = rememberEpicStoreEntry { DI.createTodoFeature(todoId) }
    val todoToggleCompletionFeature = rememberEpicStoreEntry {
        DI.createTodoToggleCompletionFeature(todoId)
    }

    val todo by todoFeature.state.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = todo?.title ?: "not found",
                style = MaterialTheme.typography.h6
            )

            Text(
                text = todo?.content ?: "not found",
                style = MaterialTheme.typography.body1
            )

            Button(
                onClick = { todoToggleCompletionFeature.toggle() },
                content = {
                    if (todo?.completed == true) {
                        Text("Completed")
                    } else {
                        Text("Complete")
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (todo?.completed == true) {
                        MaterialTheme.colors.surface
                    } else {
                        MaterialTheme.colors.primary
                    },
                    contentColor = if (todo?.completed == true) {
                        MaterialTheme.colors.onSurface
                    } else {
                        MaterialTheme.colors.onPrimary
                    }
                )
            )
        }
    }
}