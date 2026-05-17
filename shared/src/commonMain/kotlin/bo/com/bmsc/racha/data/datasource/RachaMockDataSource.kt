package bo.com.bmsc.racha.data.datasource

import bo.com.bmsc.racha.domain.model.Participant

class RachaMockDataSource {

  fun getMockParticipants(): List<Participant> {
    return listOf(
      Participant(
        id = "1",
        name = "Ana Vargas",
        initials = "AV",
        avatarColor = "#FFB3BA",
        isSelected = false
      ),
      Participant(
        id = "2",
        name = "Carlos Rojas",
        initials = "CR",
        avatarColor = "#BAE1B3",
        isSelected = false
      ),
      Participant(
        id = "3",
        name = "Lucía Mamani",
        initials = "LM",
        avatarColor = "#DCD3FF",
        isSelected = false
      ),
      Participant(
        id = "4",
        name = "Diego Sotomayor",
        initials = "DS",
        avatarColor = "#FFE5B3",
        isSelected = false
      ),
      Participant(
        id = "5",
        name = "Sofía López",
        initials = "SL",
        avatarColor = "#B3D9FF",
        isSelected = false
      ),
      Participant(
        id = "6",
        name = "Mateo Quiroz",
        initials = "MQ",
        avatarColor = "#FFB3E6",
        isSelected = false
      ),
    )
  }

  fun generateCloseDate(durationMonths: Int): String {
    // Simulación simple - en producción usarías kotlinx.datetime
    return when (durationMonths) {
      3 -> "16 ago · 2026"
      6 -> "16 nov · 2026"
      12 -> "16 may · 2027"
      else -> "16 nov · 2026"
    }
  }
}
