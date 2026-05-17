package bo.com.bmsc.racha.domain.model

import kotlinx.serialization.Serializable

/**
 * Configuración completa de una racha
 */
data class RachaConfig(
  val seasonName: String = "",
  val duration: RachaDuration = RachaDuration.SIX_MONTHS,
  val frequency: ContributionFrequency = ContributionFrequency.WEEKLY,
  val minimumAmount: Int = 20,
  val goal: SavingsGoal? = null,
  val participants: List<Participant> = emptyList(),
  val closeDate: String = "",
  val fundsLocked: Boolean = true,
)

enum class RachaDuration(val months: Int, val label: String) {
  THREE_MONTHS(3, "3 meses"),
  SIX_MONTHS(6, "6 meses"),
  TWELVE_MONTHS(12, "12 meses")
}

enum class ContributionFrequency(val label: String, val details: String) {
  DAILY("Diaria", "Todos los días"),
  WEEKLY("Semanal", "domingos"),
  BIWEEKLY("Quincenal", "Cada 15 días"),
  MONTHLY("Mensual", "Último día del mes")
}

enum class SavingsGoal(val label: String, val emoji: String) {
  TRAVEL("Viaje", "✈️"),
  LAPTOP("Laptop", "💻"),
  EMERGENCY("Emergencia", "🚨"),
  STUDIES("Estudios", "📚"),
  BUSINESS("Negocio", "💼"),
  OTHER("Otro", "⭐")
}

data class Participant(
  val id: String,
  val name: String,
  val initials: String,
  val avatarColor: String,
  val isSelected: Boolean = false,
  val isCurrentUser: Boolean = false,
)

data class RachaSummary(
  val seasonName: String,
  val goalLabel: String,
  val duration: String,
  val participantCount: Int,
  val frequency: String,
  val minimumAmount: Int,
  val closeDate: String,
  val fundsLocked: Boolean,
)

enum class InviteMethod {
  CONTACTS,
  LINK,
  QR
}

data class DynamicKey(
  val digits: List<Int> = List(6) { 0 }
)
