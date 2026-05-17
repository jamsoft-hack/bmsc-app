package bo.com.bmsc.core.database

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

actual class DatabaseFactory {
  actual fun create(): RoomDatabase.Builder<BarikDatabase> {
    val dbFile = "${fileDirectory()}/${BarikDatabase.DB_NAME}"

    return Room.databaseBuilder<BarikDatabase>(
      name = dbFile,
    )
  }

  @OptIn(ExperimentalForeignApi::class)
  private fun fileDirectory(): String {
    val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
      directory = NSDocumentDirectory,
      inDomain = NSUserDomainMask,
      appropriateForURL = null,
      create = false,
      error = null,
    )
    return requireNotNull(documentDirectory).path!!
  }
}
