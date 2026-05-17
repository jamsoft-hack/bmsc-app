package bo.com.bmsc.core.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor

@Database(
  entities = [],
  version = BarikDatabase.DB_VERSION,
  exportSchema = true
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class BarikDatabase : RoomDatabase() {
  companion object {
    const val DB_NAME = "main_bmsc.db"
    const val DB_VERSION = 7
  }
}

expect class DatabaseFactory {
  fun create(): RoomDatabase.Builder<BarikDatabase>
}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<BarikDatabase> {
  override fun initialize(): BarikDatabase
}
