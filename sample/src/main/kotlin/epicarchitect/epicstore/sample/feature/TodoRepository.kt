package epicarchitect.epicstore.sample.feature

import epicarchitect.epicstore.sample.entity.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    suspend fun insert(todo: Todo)

    suspend fun delete(id: Int)

    suspend fun toggleCompletion(id: Int)

    fun todoListFlow(): Flow<List<Todo>>

    fun generateId() = (0..100000).random()

}