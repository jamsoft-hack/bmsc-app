package bo.com.bmsc.core.locale

import android.text.format.DateFormat

actual fun formatDate(date: Long, format: String): String {
  val data = DateFormat.format(format, date);

  return data.toString();
}
