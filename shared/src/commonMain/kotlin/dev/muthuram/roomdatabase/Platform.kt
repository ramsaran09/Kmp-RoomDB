package dev.muthuram.roomdatabase

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import dev.muthuram.roomdatabase.data.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
