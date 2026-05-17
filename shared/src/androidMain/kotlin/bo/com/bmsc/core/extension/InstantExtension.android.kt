package bo.com.bmsc.core.extension

import androidx.compose.ui.text.intl.Locale
import bo.com.bmsc.core.extension.format
import kotlinx.datetime.Instant
import java.text.SimpleDateFormat
import java.util.Date

actual fun Instant.format(format: String): String {
  val formatter = SimpleDateFormat(format, Locale.current.platformLocale)

  val timeInMillis = this.toEpochMilliseconds()
  val date = Date().apply { time = timeInMillis }

  return formatter.format(date)
}
