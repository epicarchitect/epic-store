package epicarchitect.epicstore.sample.feature

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class TodoToggleCompletionFeature(
    private val coroutineScope: CoroutineScope,
    private val todoRepository: TodoRepository,
    private val todoId: Int
) {

    fun toggleCompletion() {
        coroutineScope.launch {
            todoRepository.toggleCompletion(todoId)
        }
    }
}