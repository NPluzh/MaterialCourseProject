package geekbarains.material.model.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import geekbarains.material.model.room.dao.FavoriteDao
import geekbarains.material.model.room.table.RoomFavorite

@androidx.room.Database(entities = [RoomFavorite::class], version = 1 )
abstract class Database: RoomDatabase() {

    abstract val favoriteDao: FavoriteDao

    companion object {

        private const val DB_NAME = "database.db"
        private var instance: Database? = null

        fun getInstance() = instance ?: throw  RuntimeException(
            " Database  has  not  been  created.  Please  call  create(context)"
        )

        fun create(context: Context?) {
            if (instance == null) {
                instance = Room.databaseBuilder(context!!, Database::class.java, DB_NAME)
                    .build()
            }
        }
    }
}