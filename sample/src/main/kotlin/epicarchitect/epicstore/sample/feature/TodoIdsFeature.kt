package epicarchitect.epicstore.sample.feature

import epicarchitect.epicstore.sample.repository.FakeTodoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * Feature is something like a ViewModel.
 * But the ViewModel of the entire screen often looks like a God class
 * So I tried splitting the ViewModel into Features
 * Just like we split the Repository into use cases
 * */
class TodoIdsFeature(
    coroutineScope: CoroutineScope,
    todoRepository: FakeTodoRepository
) {

    val state = todoRepository.todoListFlow().map { it.map { it.id } }
        .stateIn(coroutineScope, SharingStarted.WhileSubscribed(), emptyList())

}