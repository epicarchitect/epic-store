package epicarchitect.epicstore.sample.feature

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TodoUpdatingFeature(
    private val coroutineScope: CoroutineScope,
    private val todoRepository: TodoRepository
) {

    private val mutableState = MutableStateFlow<State>(
        State.Ready(update = ::update)
    )

    val state = mutableState.asStateFlow()

    private fun update(id: Int, title: String, content: String) {
        coroutineScope.launch {
            mutableState.update {
                State.InProcess(
                    id = id,
                    title = title,
                    content = content
                )
            }

            todoRepository.update(id, title, content)

            mutableState.update {
                State.Finished()
            }
        }
    }

    sealed class State {
        data class Ready(
            val update: (id: Int, title: String, content: String) -> Unit
        ) : State()

        data class InProcess(
            val id: Int,
            val title: String,
            val content: String
        ) : State()

        class Finished : State()
    }
}