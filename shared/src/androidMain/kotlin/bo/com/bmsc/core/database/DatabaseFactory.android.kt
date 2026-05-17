package bo.com.bmsc.core.database

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase

actual class DatabaseFactory(private val app: Application) {
  actual fun create(): RoomDatabase.Builder<BarikDatabase> {
    val databaseFile = app.getDatabasePath(BarikDatabase.DB_NAME)

    return Room.databaseBuilder<BarikDatabase>(
      context = app,
      name = databaseFile.absolutePath,
    )
  }
}
