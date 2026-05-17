package bo.com.bmsc.core.extension

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Instant

val spainTimeZone = TimeZone.of("Europe/Madrid")

fun Long.toLocalDateTime(): LocalDateTime {
  return Instant.fromEpochMilliseconds(this)
    .toLocalDateTime(TimeZone.currentSystemDefault())
}

fun Long.toLocalEpoch(timeZone: TimeZone = TimeZone.UTC): Long {
  val utcInstant = Instant.fromEpochMilliseconds(this)

  val spainDateTime = utcInstant.toLocalDateTime(timeZone)

  return spainDateTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
}

fun Instant.toLocalDate(): LocalDate {
  return this.toLocalDateTime(spainTimeZone).date
}

fun LocalDateTime.toSpainInstant(): Instant {
  return this.toInstant(spainTimeZone)
}

fun LocalDateTime.toLong(): Long {
  return toLocalInstant().toEpochMilliseconds()
}

fun LocalDateTime.toLocalInstant(): Instant {
  return toInstant(TimeZone.currentSystemDefault())
}

fun LocalDateTime.daysFrom(other: Instant): Int =
  other.startOfDay().toLocalDate().daysUntil(this.date) + 1

val LocalDateTime.daysFromNow: Int
  get() {
    val now = Clock.System.now().startOfDay().toLocalDate()

    return now.daysUntil(this.date) + 1
  }

fun LocalDateTime.modifyDays(days: Int): LocalDateTime {
  val modified = this.modifyEpochDays(days)

  return modified.toLocalDateTime()
}

fun LocalDateTime.modifyEpochDays(days: Int): Long {
  return this.toLong() + days * 24 * 60 * 60 * 1000L
}

fun LocalDateTime.addSecondsToInstant(seconds: Int): Instant {
  val instant = this.toInstant(spainTimeZone)
  return instant.plus(seconds, DateTimeUnit.SECOND)
}

fun LocalDateTime.modify(value: Int, unit: DateTimeUnit.DateBased): LocalDateTime {
  return LocalDateTime(
    date = this.date.plus(value, unit),
    time = this.time,
  )
}

/**
 * [todayPlusMonth] returns current trust time + 29 days
 */
val todayPlusMonth: LocalDate
  get() = Clock.System.now().toLocalDate().plus(period = DatePeriod(days = 29))
