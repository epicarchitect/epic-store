package epicarchitect.epicstore.sample.feature

import epicarchitect.epicstore.sample.repository.FakeTodoRepository
import epicarchitect.epicstore.sample.entity.Todo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * This is something like ViewModel
 * */
class TodoCreationFeature(
    private val coroutineScope: CoroutineScope,
    private val todoRepository: FakeTodoRepository
) {

    val title = MutableStateFlow("")
    val content = MutableStateFlow("")

    fun startCreation() {
        coroutineScope.launch {
            todoRepository.insert(
                Todo(
                    id = todoRepository.generateId(),
                    title = title.value,
                    content = content.value,
                    completed = false
                )
            )
        }
    }

}