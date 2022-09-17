package epicarchitect.epicstore.sample.feature

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TodoTitleInputFeature(
    private val coroutineScope: CoroutineScope,
    private val todoRepository: TodoRepository,
    private val maxLength: Int
) {

    private val mutableState = MutableStateFlow(
        State(
            input = "",
            initialInput = "",
            validation = Validation.Initial(),
            maxLength = maxLength
        )
    )

    private var validationJob: Job? = null

    val state = mutableState.asStateFlow()

    fun updateInitialInput(value: String) {
        mutableState.update {
            it.copy(
                input = value,
                initialInput = value
            )
        }
    }

    fun updateInput(value: String) {
        mutableState.update {
            it.copy(
                input = value
            )
        }
    }

    fun validate() {
        mutableState.update {
            it.copy(
                validation = Validation.InProcess()
            )
        }

        validationJob?.cancel()
        validationJob = coroutineScope.launch {
            mutableState.update {
                it.copy(
                    validation = when {
                        it.input == it.initialInput -> Validation.Initial()
                        it.input.isEmpty() -> Validation.Empty()
                        it.input.length > maxLength -> Validation.TooLong()
                        todoRepository.isTodoTitleUsed(it.input) -> Validation.AlreadyUsed()
                        else -> Validation.Valid()
                    }
                )
            }
        }
    }

    data class State(
        val input: String,
        val initialInput: String,
        val validation: Validation,
        val maxLength: Int
    )

    sealed class Validation {
        class Initial : Validation()
        class InProcess : Validation()
        class Valid : Validation()
        class Empty : Validation()
        class TooLong : Validation()
        class AlreadyUsed : Validation()
    }
}