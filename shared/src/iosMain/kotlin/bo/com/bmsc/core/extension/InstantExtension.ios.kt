package bo.com.bmsc.core.extension

import kotlinx.datetime.Instant
import platform.Foundation.NSDateFormatter
import platform.Foundation.dateWithTimeIntervalSince1970

actual fun Instant.format(format: String): String {
    val dateFormatter = NSDateFormatter()
    dateFormatter.dateFormat = format
    val date = platform.Foundation.NSDate.dateWithTimeIntervalSince1970(this.epochSeconds.toDouble())
    return dateFormatter.stringFromDate(date)
}