package bo.com.bmsc.core.locale

import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSLocale
import platform.Foundation.NSTimeZone
import platform.Foundation.dateWithTimeIntervalSince1970
import platform.Foundation.localTimeZone

actual fun formatDate(date: Long, format: String): String {
  val nativeDate = NSDate.dateWithTimeIntervalSince1970(date.toDouble() / 1000)

  val dateFormatter = NSDateFormatter()

  dateFormatter.timeZone = NSTimeZone.localTimeZone
  dateFormatter.locale = NSLocale(IosAppLocaleManager().getLocale().code)
  dateFormatter.dateFormat = format

  return dateFormatter.stringFromDate(nativeDate)
}
