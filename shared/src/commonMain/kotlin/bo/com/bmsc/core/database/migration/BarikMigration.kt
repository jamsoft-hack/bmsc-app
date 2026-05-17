package bo.com.bmsc.core.database.migration

import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection

abstract class BarikMigration(startVersion: Int, endVersion: Int) :
  Migration(startVersion, endVersion) {
  protected fun columnExists(
    database: SQLiteConnection,
    tableName: String,
    columnName: String
  ): Boolean {
    val stmt = database.prepare("PRAGMA table_info($tableName)")
    stmt.use {
      while (it.step()) {
        val name = it.getText(1)
        if (name == columnName) {
          return true
        }
      }
    }

    return false
  }
}
