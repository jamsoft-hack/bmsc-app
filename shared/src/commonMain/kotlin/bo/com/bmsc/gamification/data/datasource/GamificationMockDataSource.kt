package bo.com.bmsc.gamification.data.datasource

import bo.com.bmsc.gamification.domain.model.Badge
import bo.com.bmsc.gamification.domain.model.GamificationData
import bo.com.bmsc.gamification.domain.model.NextContribution
import bo.com.bmsc.gamification.domain.model.Participant
import bo.com.bmsc.gamification.domain.model.ParticipantStatus
import bo.com.bmsc.gamification.domain.model.RankingItem
import bo.com.bmsc.gamification.domain.model.StreakInfo
import bo.com.bmsc.gamification.domain.model.WeeklyProgress

class GamificationMockDataSource {

  fun getGamificationData(): GamificationData = GamificationData(
    streakInfo = StreakInfo(
      days = 47,
      status = "Vamos por la racha",
      groupName = "Los Ahorradores",
      isActive = true,
    ),
    weeklyProgress = WeeklyProgress(
      title = "Esta semana",
      completed = 3,
      total = 4,
      participants = listOf(
        Participant(
          id = "1",
          name = "Ana",
          initial = "A",
          status = ParticipantStatus.COMPLETED,
        ),
        Participant(
          id = "2",
          name = "Carlos",
          initial = "C",
          status = ParticipantStatus.COMPLETED,
        ),
        Participant(
          id = "3",
          name = "Vos",
          initial = "V",
          status = ParticipantStatus.COMPLETED,
        ),
        Participant(
          id = "4",
          name = "Lucia",
          initial = "L",
          status = ParticipantStatus.PENDING,
        ),
      ),
    ),
    nextContribution = NextContribution(
      date = "18",
      dayOfWeek = "Domingo",
      amount = "20",
      totalSavings = "720",
    ),
    badges = listOf(
      Badge(
        id = "1",
        name = "Primera semana",
        description = "Completaste tu primera semana de ahorros",
        icon = "first_week",
        isUnlocked = true,
        progress = 1f,
      ),
      Badge(
        id = "2",
        name = "Ahorrador constante",
        description = "4 semanas seguidas de ahorros",
        icon = "consistent_saver",
        isUnlocked = true,
        progress = 1f,
      ),
      Badge(
        id = "3",
        name = "Racha de fuego",
        description = "Alcanza 50 días de racha",
        icon = "fire_streak",
        isUnlocked = false,
        progress = 0.94f,
      ),
      Badge(
        id = "4",
        name = "Meta mil",
        description = "Alcanza Bs 1000 en ahorros",
        icon = "goal_1000",
        isUnlocked = false,
        progress = 0.72f,
      ),
    ),
    ranking = listOf(
      RankingItem(
        id = "1",
        name = "María González",
        initial = "M",
        score = "2450",
        position = 1,
      ),
      RankingItem(
        id = "2",
        name = "Carlos Ruiz",
        initial = "C",
        score = "2180",
        position = 2,
      ),
      RankingItem(
        id = "3",
        name = "Vos",
        initial = "V",
        score = "1950",
        position = 3,
        isCurrentUser = true,
      ),
      RankingItem(
        id = "4",
        name = "Ana López",
        initial = "A",
        score = "1820",
        position = 4,
      ),
      RankingItem(
        id = "5",
        name = "Pedro Sánchez",
        initial = "P",
        score = "1650",
        position = 5,
      ),
    ),
  )
}
