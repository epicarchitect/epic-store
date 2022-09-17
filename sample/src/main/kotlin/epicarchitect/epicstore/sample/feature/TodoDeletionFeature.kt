package epicarchitect.epicstore.sample.feature

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * This is something like ViewModel
 * */
class TodoDeletionFeature(
    private val coroutineScope: CoroutineScope,
    private val todoRepository: TodoRepository,
    private val todoId: Int
) {

    fun startDeletion() {
        coroutineScope.launch {
            todoRepository.delete(todoId)
        }
    }

}