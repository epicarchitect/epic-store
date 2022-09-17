package epicarchitect.epicstore.sample.repository

import epicarchitect.epicstore.sample.entity.Todo
import epicarchitect.epicstore.sample.feature.TodoRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet

class FakeTodoRepository : TodoRepository {

    private var lastGeneratedId = 0
    private val state = MutableStateFlow(emptyList<Todo>())

    override suspend fun create(
        title: String,
        content: String
    ) {
        hardWork()
        state.update { list ->
            list + Todo(
                id = generateId(),
                title = title,
                content = content,
                completed = false
            )
        }
    }

    override suspend fun update(
        id: Int,
        title: String,
        content: String
    ) {
        hardWork()
        state.update { list ->
            list.map { todo ->
                if (todo.id == id) todo.copy(
                    title = title,
                    content = content
                )
                else todo
            }
        }
    }

    override suspend fun delete(id: Int) {
        hardWork()
        state.update { it.filter { it.id != id } }
    }

    override suspend fun toggleCompletion(id: Int) {
        hardWork()
        state.update {
            it.map { todo ->
                if (todo.id == id) todo.copy(completed = !todo.completed)
                else todo
            }
        }
    }

    override suspend fun getTodo(id: Int): Todo {
        hardWork()
        return state.value.first { it.id == id }
    }

    override fun todoFlow(id: Int) = state.map {
        hardWork()
        it.find { it.id == id }
    }

    override fun todoIdsFlow() = state.map {
        hardWork()
        it.map { it.id }
    }

    private suspend fun generateId(): Int {
        hardWork()
        return ++lastGeneratedId
    }

    override suspend fun isTodoTitleUsed(title: String): Boolean {
        hardWork()
        return state.value.find { it.title == title } != null
    }

    private suspend fun hardWork() {
        delay((500L..3000L).random())
    }
}