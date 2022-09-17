package epicarchitect.epicstore.sample.repository

import epicarchitect.epicstore.sample.entity.Todo
import epicarchitect.epicstore.sample.feature.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class FakeTodoRepository : TodoRepository {

    private val state = MutableStateFlow<List<Todo>>(
        List(10) {
            Todo(
                id = it,
                title = "Item $it",
                content = "Item $it",
                completed = it % 2 == 0
            )
        }
    )

    override suspend fun insert(todo: Todo) = state.update { it + todo }

    override suspend fun delete(id: Int) = state.update { it.filter { it.id != id } }

    override suspend fun toggleCompletion(id: Int) {
        state.update {
            it.map {
                if (it.id == id) it.copy(completed = it.completed.not())
                else it
            }
        }
    }

    override fun todoListFlow(): Flow<List<Todo>> = state

    override fun generateId() = (0..100000).random()

}