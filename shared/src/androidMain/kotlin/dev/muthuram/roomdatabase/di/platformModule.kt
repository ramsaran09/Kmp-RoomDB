package dev.muthuram.roomdatabase.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import dev.muthuram.roomdatabase.data.db.AppDatabase
import dev.muthuram.roomdatabase.data.db.DATA_STORE_FILE_NAME
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual fun platformModule() = module {
    single<AppDatabase> { getDatabaseBuilder(androidApplication()) }
    singleOf(::createDataStore)
}


fun getDatabaseBuilder(context: Context): AppDatabase {
    val dbFile = context.getDatabasePath("user.db")
    return Room.databaseBuilder<AppDatabase>(context.applicationContext, dbFile.absolutePath)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}

fun createDataStore(context: Context): DataStore<Preferences> {
    return dev.muthuram.roomdatabase.data.db.createDataStore {
        context.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath
    }
}