package geekbarains.material.model.room.dao

import androidx.room.*
import geekbarains.material.model.room.table.RoomFavorite

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(roomFavorite: RoomFavorite)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg roomFavorite: RoomFavorite)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(roomFavorites:List<RoomFavorite>)

    @Delete
    fun delete(roomFavorite: RoomFavorite)

    @Delete
    fun delete(vararg roomFavorite: RoomFavorite)

    @Delete
    fun delete(roomFavorites:List<RoomFavorite>)

    @Update
    fun update(roomFavorite: RoomFavorite)
    @Update
    fun update(vararg roomFavorite: RoomFavorite)
    @Update
    fun update(roomFavorites:List<RoomFavorite>)

    @Query("SELECT * FROM RoomFavorite")
    fun getAll():List<RoomFavorite>

    @Query("SELECT * FROM RoomFavorite WHERE date = :favoriteDate LIMIT 1 ")
    fun findByDate(favoriteDate:String): RoomFavorite

}