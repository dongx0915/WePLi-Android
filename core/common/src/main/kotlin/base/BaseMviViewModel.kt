package base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

abstract class BaseMviViewModel<S : UiState, E : SideEffect, I : Intent>(
    initialState: S
) : ContainerHost<S, E>, ViewModel() {

    override val container: Container<S, E> = container(initialState)

    open val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("ExceptionHandler", throwable.toString())
        throw throwable
    }

    protected inline fun <STATE : Any> ContainerHost<STATE, *>.updateState(
        crossinline reducer: STATE.() -> STATE
    ) {
        intent {
            reduce { state.reducer() }
        }
    }

    fun launch(
        dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        exceptionHandler: CoroutineExceptionHandler? = null,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        val coroutineContext = exceptionHandler?.let {
            dispatcher + it
        } ?: dispatcher

        return viewModelScope.launch(coroutineContext, start, block)
    }

    fun launchWithHandler(
        dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        exceptionHandler: CoroutineExceptionHandler = this.exceptionHandler,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return viewModelScope.launch(dispatcher + exceptionHandler, start, block)
    }

    fun processIntent(vararg intents: I) {
        intents.forEach(::processIntent)
    }

    abstract fun processIntent(intent: I)
}