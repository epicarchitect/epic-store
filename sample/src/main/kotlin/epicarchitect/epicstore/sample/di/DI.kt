package epicarchitect.epicstore.sample.di

import epicarchitect.epicstore.sample.feature.TodoContentInputFeature
import epicarchitect.epicstore.sample.feature.TodoCreationFeature
import epicarchitect.epicstore.sample.feature.TodoDeletionFeature
import epicarchitect.epicstore.sample.feature.TodoFeature
import epicarchitect.epicstore.sample.feature.TodoIdsFeature
import epicarchitect.epicstore.sample.feature.TodoTitleInputFeature
import epicarchitect.epicstore.sample.feature.TodoToggleCompletionFeature
import epicarchitect.epicstore.sample.feature.TodoUpdatingFeature
import epicarchitect.epicstore.sample.repository.FakeTodoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

object DI {
    private val defaultCoroutineScope = CoroutineScope(Dispatchers.Default)
    private val fakeTodoRepository = FakeTodoRepository()

    fun createTodoCreationFeature() = TodoCreationFeature(
        defaultCoroutineScope,
        fakeTodoRepository
    )

    fun createTodoUpdatingFeature() = TodoUpdatingFeature(
        defaultCoroutineScope,
        fakeTodoRepository
    )

    fun createTodoDeletionFeature(todoId: Int) = TodoDeletionFeature(
        defaultCoroutineScope,
        fakeTodoRepository,
        todoId
    )

    fun createTodoIdsFeature() = TodoIdsFeature(
        defaultCoroutineScope,
        fakeTodoRepository
    )

    fun createTodoFeature(todoId: Int) = TodoFeature(
        defaultCoroutineScope,
        fakeTodoRepository,
        todoId
    )

    fun createTodoToggleCompletionFeature(todoId: Int) = TodoToggleCompletionFeature(
        defaultCoroutineScope,
        fakeTodoRepository,
        todoId
    )

    fun createTodoTitleInputFeature() = TodoTitleInputFeature(
        coroutineScope = defaultCoroutineScope,
        todoRepository = fakeTodoRepository,
        maxLength = 20
    )

    fun createTodoContentInputFeature() = TodoContentInputFeature()
}