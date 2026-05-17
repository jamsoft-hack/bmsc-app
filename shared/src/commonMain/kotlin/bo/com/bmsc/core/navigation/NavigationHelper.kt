package bo.com.bmsc.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import bo.com.bmsc.Route
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import org.koin.compose.koinInject

/**
 * Navigation helper that allows navigation from any screen or composable.
 * Uses a SharedFlow to emit navigation events that are collected in the App composable.
 */
class NavigationHelper {

  private val _navigationEvents = MutableSharedFlow<NavigationEvent>(
    replay = 1,
    onBufferOverflow = BufferOverflow.DROP_OLDEST
  )

  /**
   * Flow of navigation events that should be collected in the App composable
   */
  val navigationEvents: Flow<NavigationEvent> = _navigationEvents

  /**
   * Navigate to a specific route
   */
  fun navigateTo(route: Route) {
    _navigationEvents.tryEmit(NavigationEvent.NavigateTo(route))
  }

  /**
   * Navigate back
   */
  fun navigateBack() {
    _navigationEvents.tryEmit(NavigationEvent.NavigateBack)
  }

  /**
   * Replace current destination with new route (clears back stack)
   */
  fun replaceWith(route: Route) {
    _navigationEvents.tryEmit(NavigationEvent.Replace(route))
  }
}

/**
 * Sealed class representing different navigation events
 */
sealed class NavigationEvent {
  data class NavigateTo(val route: Route) : NavigationEvent()
  data object NavigateBack : NavigationEvent()
  data class Replace(val route: Route) : NavigationEvent()
}

/**
 * Convenience composable to get the NavigationHelper.
 * Can be used in any composable to trigger navigation.
 */
@Composable
fun rememberNavigationHelper(
  navigationHelper: NavigationHelper = koinInject()
): NavigationHelper = remember { navigationHelper }
