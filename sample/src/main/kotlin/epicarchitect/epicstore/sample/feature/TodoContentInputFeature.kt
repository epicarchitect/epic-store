package epicarchitect.epicstore.sample.feature

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TodoContentInputFeature {

    private val mutableState = MutableStateFlow(
        State(
            input = "",
            initialInput = "",
            validation = Validation.Initial()
        )
    )

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
                validation = when {
                    it.input == it.initialInput -> Validation.Initial()
                    it.input.isEmpty() -> Validation.Empty()
                    else -> Validation.Valid()
                }
            )
        }
    }

    data class State(
        val input: String,
        val initialInput: String,
        val validation: Validation
    )

    sealed class Validation {
        class Initial : Validation()
        class Valid : Validation()
        class Empty : Validation()
    }
}