package epicarchitect.epicstore.sample.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import epicarchitect.epicstore.compose.rememberEpicStoreEntry
import epicarchitect.epicstore.sample.di.DI
import epicarchitect.epicstore.sample.feature.TodoContentInputFeature
import epicarchitect.epicstore.sample.feature.TodoCreationFeature
import epicarchitect.epicstore.sample.feature.TodoTitleInputFeature

@Composable
fun TodoCreationScreen(onFinished: () -> Unit) {
    val creationFeature = rememberEpicStoreEntry { DI.createTodoCreationFeature() }
    val creationState by creationFeature.state.collectAsState()

    when (val state = creationState) {
        is TodoCreationFeature.State.Ready -> {
            CreationInputContent(creationFeature::create)
        }
        is TodoCreationFeature.State.InProcess -> {
            CreationInProcessContent(state)
        }
        is TodoCreationFeature.State.Finished -> {
            CreationFinishedContent(onFinished)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun CreationInputContent(
    onDone: (title: String, content: String) -> Unit
) {
    val titleFeature = rememberEpicStoreEntry { DI.createTodoTitleInputFeature() }
    val contentFeature = rememberEpicStoreEntry { DI.createTodoContentInputFeature() }

    val titleState by titleFeature.state.collectAsState()
    val contentState by contentFeature.state.collectAsState()

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val (titleFocusRequester, contentFocusRequester) = FocusRequester.createRefs()

    LaunchedEffect(true) {
        titleFocusRequester.requestFocus()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Todo Creation",
            style = MaterialTheme.typography.h6
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(titleFocusRequester),
            value = titleState.input,
            onValueChange = {
                titleFeature.updateInput(it)
                titleFeature.validate()
            },
            label = {
                Text("Title")
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    contentFocusRequester.requestFocus()
                }
            )
        )

        when (titleState.validation) {
            is TodoTitleInputFeature.Validation.AlreadyUsed -> {
                Text(text = "Title already used")
            }
            is TodoTitleInputFeature.Validation.Empty -> {
                Text(text = "Title can't be empty")
            }
            is TodoTitleInputFeature.Validation.InProcess -> {
                Text(text = "Validation...")
            }
            is TodoTitleInputFeature.Validation.TooLong -> {
                Text(text = "The title cannot be longer than ${titleState.maxLength} characters")
            }
            else -> {
                /* no-op */
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(contentFocusRequester),
            value = contentState.input,
            onValueChange = {
                contentFeature.updateInput(it)
                contentFeature.validate()
            },
            label = {
                Text("Content")
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                }
            )
        )

        when (contentState.validation) {
            is TodoContentInputFeature.Validation.Empty -> {
                Text(text = "Content can't be empty")
            }
            else -> {
                /* no-op */
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            modifier = Modifier
                .align(Alignment.End)
                .padding(16.dp),
            onClick = {
                onDone(
                    titleState.input,
                    contentState.input
                )
            },
            enabled = titleState.validation is TodoTitleInputFeature.Validation.Valid
                    && contentState.validation is TodoContentInputFeature.Validation.Valid,
            content = {
                Text("Done")
            }
        )
    }
}

@Composable
private fun CreationInProcessContent(
    creationState: TodoCreationFeature.State.InProcess
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text("Creating todo with:")
            Text("Title: ${creationState.title}")
            Text("Content: ${creationState.content}")

            Spacer(modifier = Modifier.height(16.dp))

            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
private fun CreationFinishedContent(
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text("Todo created!")

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = onBackClick,
                content = {
                    Text("Go back")
                }
            )
        }
    }
}