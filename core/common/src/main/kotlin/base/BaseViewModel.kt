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
import kotlinx.coroutines.launch

abstract class BaseViewModel: ViewModel() {

    open val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("ExceptionHandler", throwable.toString())
        throw throwable
    }

    fun launchWithHandler(
        dispatcher: CoroutineDispatcher = Dispatchers.Main,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        exceptionHandler: CoroutineExceptionHandler = this.exceptionHandler,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return viewModelScope.launch(dispatcher + exceptionHandler, start, block)
    }
}