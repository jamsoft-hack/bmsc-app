package bo.com.bmsc.core.state

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

sealed interface AppUiEffect {
}

object AppUiEffects {
    private val _effects = MutableSharedFlow<AppUiEffect>()
    val effects = _effects.asSharedFlow()

    private val scope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())

    fun send(effect: AppUiEffect) {
        scope.launch {
            _effects.emit(effect)
        }
    }
}