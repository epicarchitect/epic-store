package epicarchitect.epicstore.sample.feature

import epicarchitect.epicstore.sample.repository.FakeTodoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class TodoIdsFeature(
    coroutineScope: CoroutineScope,
    todoRepository: FakeTodoRepository
) {

    val state = todoRepository.todoIdsFlow().map {
        State.Loaded(it)
    }.stateIn(coroutineScope, SharingStarted.WhileSubscribed(), State.Loading())

    sealed class State {
        class Loading : State()
        data class Loaded(val todoIds: List<Int>) : State()
    }
}