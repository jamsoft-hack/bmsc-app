package bo.com.bmsc.core.extension

import bo.com.bmsc.core.locale.AppLang
import bo.com.bmsc.core.locale.currentLanguage
import bo.com.bmsc.core.locale.formatDate
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.atTime
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.until

/** Calculates the number of days between the current DateTime object and another date.
 *
 * This method returns a positive integer if the other date is in the future,
 * and a negative integer if it's in the past.
 *
 * */
fun Instant.daysBetween(anotherDate: Instant): Int {
  return this.until(anotherDate, DateTimeUnit.DAY, spainTimeZone).toInt()
}

fun Instant.toDayMonthDate(): String {
  return this.format("dd MMM")
}

fun Instant.toSimpleHumanDate() = this.format("dd/MM/yyyy")

fun Instant.literalMonth() = this.format("MMMM")

fun Instant.toMonthDate(): String {
  val locale = currentLanguage()
  val formatted = formatDate(toEpochMilliseconds(), locale.monthDateFormat)

  return if (locale == AppLang.Euskera) formatted.replaceFirstChar { it.titlecase() } else formatted
}

fun Instant.toHumanDate(): String {
  val locale = currentLanguage()
  val formatted = formatDate(toEpochMilliseconds(), currentLanguage().humanDateFormat)

  return if (locale == AppLang.Euskera) formatted.replaceFirstChar { it.titlecase() } else formatted
}

fun Instant.startOfDay(): Instant {
  return this.toLocalDateTime(TimeZone.currentSystemDefault()).date.atStartOfDayIn(TimeZone.currentSystemDefault())
}

fun Instant.endOfDay(): Instant {
  return this.toLocalDateTime(TimeZone.currentSystemDefault()).date
    .atTime(hour = 23, minute = 59, second = 58)
    .toInstant(TimeZone.currentSystemDefault())
}

expect fun Instant.format(format: String): String