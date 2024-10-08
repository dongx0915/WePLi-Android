package common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun LifecycleOwner.withInMainScope(
    coroutineContext: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    action: suspend () -> Unit
) {
    lifecycleScope.launch(
        context = Dispatchers.Main + coroutineContext,
        start = start,
    ) {
        action()
    }
}

fun LifecycleOwner.withInIOScope(
    coroutineContext: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    action: suspend () -> Unit
) {
    lifecycleScope.launch(
        context = Dispatchers.IO + coroutineContext,
        start = start,
    ) {
        action()
    }
}