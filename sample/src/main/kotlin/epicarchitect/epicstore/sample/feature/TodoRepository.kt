package epicarchitect.epicstore.sample.feature

import epicarchitect.epicstore.sample.entity.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    suspend fun create(title: String, content: String)

    suspend fun update(id: Int, title: String, content: String)

    suspend fun delete(id: Int)

    suspend fun toggleCompletion(id: Int)

    suspend fun getTodo(id: Int): Todo

    fun todoFlow(id: Int): Flow<Todo?>

    fun todoIdsFlow(): Flow<List<Int>>

    suspend fun isTodoTitleUsed(title: String): Boolean

}