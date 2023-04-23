package geekbarains.material

import android.app.Application
import geekbarains.material.model.room.Database

class App:Application() {
    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Database.create(this)
    }
}