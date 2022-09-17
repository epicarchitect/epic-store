package epicarchitect.epicstore.sample.feature

import epicarchitect.epicstore.sample.entity.Todo
import epicarchitect.epicstore.sample.repository.FakeTodoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class TodoFeature(
    coroutineScope: CoroutineScope,
    todoRepository: FakeTodoRepository,
    todoId: Int
) {

    val state = todoRepository.todoFlow(todoId).map {
        if (it == null) State.NotFound()
        else State.Loaded(it)
    }.stateIn(coroutineScope, SharingStarted.WhileSubscribed(), State.Loading())

    sealed class State {
        class Loading : State()
        data class Loaded(val todo: Todo) : State()
        class NotFound : State()
    }
}