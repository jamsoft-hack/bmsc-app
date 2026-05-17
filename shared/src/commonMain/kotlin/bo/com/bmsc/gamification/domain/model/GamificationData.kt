package bo.com.bmsc.gamification.domain.model

data class GamificationData(
  val streakInfo: StreakInfo,
  val weeklyProgress: WeeklyProgress,
  val nextContribution: NextContribution,
  val badges: List<Badge>,
  val ranking: List<RankingItem>,
)

data class StreakInfo(
  val days: Int,
  val status: String,
  val groupName: String,
  val isActive: Boolean,
)

data class WeeklyProgress(
  val title: String,
  val completed: Int,
  val total: Int,
  val participants: List<Participant>,
)

data class Participant(
  val id: String,
  val name: String,
  val initial: String,
  val status: ParticipantStatus,
)

enum class ParticipantStatus {
  COMPLETED,
  PENDING,
}

data class NextContribution(
  val date: String,
  val dayOfWeek: String,
  val amount: String,
  val totalSavings: String,
  val currency: String = "Bs",
)

data class Badge(
  val id: String,
  val name: String,
  val description: String,
  val icon: String,
  val isUnlocked: Boolean,
  val progress: Float = 0f,
)

data class RankingItem(
  val id: String,
  val name: String,
  val initial: String,
  val score: String,
  val position: Int,
  val isCurrentUser: Boolean = false,
)
