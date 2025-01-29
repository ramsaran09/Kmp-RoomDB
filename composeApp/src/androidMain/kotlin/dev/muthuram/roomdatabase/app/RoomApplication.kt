package dev.muthuram.roomdatabase.app

import android.app.Application
import dev.muthuram.roomdatabase.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class RoomApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger()
            androidContext(this@RoomApplication)
        }
    }
}