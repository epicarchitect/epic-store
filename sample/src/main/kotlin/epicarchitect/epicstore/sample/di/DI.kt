package epicarchitect.epicstore.sample.di

import epicarchitect.epicstore.sample.feature.TodoCreationFeature
import epicarchitect.epicstore.sample.feature.TodoDeletionFeature
import epicarchitect.epicstore.sample.feature.TodoFeature
import epicarchitect.epicstore.sample.feature.TodoIdsFeature
import epicarchitect.epicstore.sample.feature.TodoToggleCompletionFeature
import epicarchitect.epicstore.sample.repository.FakeTodoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

object DI {
    val todoRepository = FakeTodoRepository()

    fun createTodoCreationFeature() = TodoCreationFeature(
        CoroutineScope(Dispatchers.Default),
        todoRepository
    )

    fun createTodoDeletionFeature(todoId: Int) = TodoDeletionFeature(
        CoroutineScope(Dispatchers.Default),
        todoRepository,
        todoId
    )

    fun createTodoIdsFeature() = TodoIdsFeature(
        CoroutineScope(Dispatchers.Default),
        todoRepository
    )

    fun createTodoFeature(todoId: Int) = TodoFeature(
        CoroutineScope(Dispatchers.Default),
        todoRepository,
        todoId
    )

    fun createTodoToggleCompletionFeature(todoId: Int) = TodoToggleCompletionFeature(
        CoroutineScope(Dispatchers.Default),
        todoRepository,
        todoId
    )
}