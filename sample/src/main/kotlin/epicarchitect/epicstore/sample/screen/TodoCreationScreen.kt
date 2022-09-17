package epicarchitect.epicstore.sample.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import epicarchitect.epicstore.compose.rememberEpicStoreEntry
import epicarchitect.epicstore.sample.di.DI

@Composable
fun TodoCreationScreen(
    onTodoCreated: () -> Unit
) {
    val todoCreationFeature = rememberEpicStoreEntry {
        DI.createTodoCreationFeature()
    }
    val title by todoCreationFeature.title.collectAsState()
    val content by todoCreationFeature.content.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = title,
            onValueChange = { todoCreationFeature.title.value = it }
        )

        TextField(
            value = content,
            onValueChange = { todoCreationFeature.content.value = it }
        )

        Button(
            onClick = {
                todoCreationFeature.startCreation()
                onTodoCreated()
            },
            content = {
                Text("Create todo")
            }
        )
    }
}