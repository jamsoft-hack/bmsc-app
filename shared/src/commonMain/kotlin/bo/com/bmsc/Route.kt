package bo.com.bmsc

import kotlinx.serialization.Serializable

interface Route {
  @Serializable
  data object Splash : Route

  @Serializable
  data object Home : Route

  @Serializable
  data class Onboarding(val modality: String): Route

  @Serializable
  data object Movements : Route

  @Serializable
  data object ModalityManagement : Route

  @Serializable
  data class HomeGraph(var modality: String? = null, var showInitPopup: Boolean = true) : Route

  @Serializable
  data object HomeCardDetail : Route

  @Serializable
  data object TripInProgress : Route

  @Serializable
  data object Logout : Route

  @Serializable
  data object SwitchToGizaScreen : Route

  @Serializable
  data object PendingOperation : Route

  @Serializable
  data object Gamification : Route
}