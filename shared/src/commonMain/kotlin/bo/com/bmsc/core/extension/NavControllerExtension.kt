package bo.com.bmsc.core.extension

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptionsBuilder
import bo.com.bmsc.Route

fun NavController.safeNavigate(
  route: Route,
  builder: NavOptionsBuilder.() -> Unit = {},
) {
  val lifecycle = currentBackStackEntry?.lifecycle
  if (lifecycle == null || lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
    navigate(route) {
      launchSingleTop = true
      builder()
    }
  }
}

fun NavController.safePopBackStack(): Boolean {
  val lifecycle = currentBackStackEntry?.lifecycle
  return if (lifecycle == null || lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
    popBackStack()
  } else {
    false
  }
}

fun NavController.replace(route: Route, keepState: Boolean = false) {
  this.safeNavigate(route) {
    popUpTo(if (keepState) graph.findStartDestination().id else 0) {
      inclusive = !keepState
      saveState = keepState
    }
  }
}
