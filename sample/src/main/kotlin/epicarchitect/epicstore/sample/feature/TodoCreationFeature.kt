package epicarchitect.epicstore.sample.feature

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TodoCreationFeature(
    private val coroutineScope: CoroutineScope,
    private val todoRepository: TodoRepository
) {

    private val mutableState = MutableStateFlow<State>(State.Ready())

    val state = mutableState.asStateFlow()

    fun create(title: String, content: String) {
        coroutineScope.launch {
            mutableState.update {
                State.InProcess(
                    title = title,
                    content = content
                )
            }

            todoRepository.create(title, content)

            mutableState.update {
                State.Finished()
            }
        }
    }

    sealed class State {
        class Ready : State()

        data class InProcess(
            val title: String,
            val content: String
        ) : State()

        class Finished : State()
    }
}