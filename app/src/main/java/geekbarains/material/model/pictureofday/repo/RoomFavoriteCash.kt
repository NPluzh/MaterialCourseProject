package geekbarains.material.model.pictureofday.repo

import android.util.Log
import geekbarains.material.model.room.Database
import geekbarains.material.model.room.Favorite
import geekbarains.material.model.room.table.RoomFavorite
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RoomFavoriteCash(val db: Database):
    IRoomFavoriteCash {

    companion object {
        const val TAG = "33333"
    }

    override fun addToFavorite(favorite: Favorite): Completable = Completable.create { emitter->
        add(favorite). let{
            if(it){
                emitter.onComplete()
               // Log.d(TAG, "RoomStateCash addToFavorite emitter.onComplete()")
            }else{
                emitter.onError(RuntimeException(" Ошибка при добавлении в избранное "))
            }
        }
    }.subscribeOn(Schedulers.io())

    override fun removeFavorite(favorite: Favorite): Completable = Completable.create { emitter->
        removeFavor(favorite). let{
            if(it){
                emitter.onComplete()
                //Log.d(TAG, "RoomStateCash addToFavorite emitter.onComplete()")
            }else{
                emitter.onError(RuntimeException("Ошибка при удалении из избранного"))
            }
        }
    }.subscribeOn(Schedulers.io())

    override fun loadFavorite(): Single<MutableList<Favorite>> = Single.fromCallable {
        db.favoriteDao.getAll().map {roomFavorite->
            Favorite(roomFavorite.date, roomFavorite.title,
                roomFavorite.url, roomFavorite.type, roomFavorite.description )
        }.toMutableList()
    }
        .subscribeOn(Schedulers.io())

    override fun isFavorite(favorite: Favorite): Single<Boolean> {
        return Single.fromCallable {
            var rf: RoomFavorite? = null
            favorite.date?.let {
                rf = db.favoriteDao.findByDate(it)
            }
            return@fromCallable rf!=null
        }
    }

    override fun saveDescription(description: String, favorite: Favorite) = Single.fromCallable {
        var roomFavorite: RoomFavorite? = null
        favorite.date?.let { roomFavorite = db.favoriteDao.findByDate(it) }
        roomFavorite?.let {
            it.description = description
            db.favoriteDao.update(it)
        }

        db.favoriteDao.getAll().map {favorites->
            Favorite(favorites.date, favorites.title,
                favorites.url, favorites.type, favorites.description)
        }.toMutableList()
    }
        .subscribeOn(Schedulers.io())

    private fun removeFavor(favorite: Favorite): Boolean{
        var roomFavorite:RoomFavorite? = null
        favorite.date?. let{roomFavorite = db.favoriteDao.findByDate(it)}
        roomFavorite?. let{
            db.favoriteDao.delete(it)
            //если вернётся null - значит удаление успешно
            favorite.date?. let{
                Log.d(
                    TAG, "!!! RoomFavoriteCash removeFavor favorite.date =" +
                        "  ${db.favoriteDao.findByDate(it)}")
            }
            return true
        } ?:return false
    }


    private fun add(favorite: Favorite):Boolean {
        val roomFavorite = RoomFavorite(
            date = favorite.date ?: "***",
            title = favorite.title ?: "***",
            url = favorite.url ?: "***",
            type = favorite.type ?: "***",
            description = favorite.description ?: "***"
        )
        //если в базе такой записи нет - пишем
        favorite.date?. let{
            val rs:RoomFavorite? =   db.favoriteDao.findByDate(it)
            rs?: let{
                db.favoriteDao.insert(roomFavorite)
                return true
            }
        }
        return false
    }
}