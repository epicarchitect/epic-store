package epicarchitect.epicstore.sample.feature

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class TodoDeletionFeature(
    private val coroutineScope: CoroutineScope,
    private val todoRepository: TodoRepository,
    private val todoId: Int
) {

    fun delete() {
        coroutineScope.launch {
            todoRepository.delete(todoId)
        }
    }
}