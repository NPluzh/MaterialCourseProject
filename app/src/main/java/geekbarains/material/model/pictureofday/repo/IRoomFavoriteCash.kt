package geekbarains.material.model.pictureofday.repo

import geekbarains.material.model.room.Favorite
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface IRoomFavoriteCash {
    fun addToFavorite(favorite: Favorite): Completable
    fun removeFavorite(favorite: Favorite): Completable

    fun loadFavorite(): Single<MutableList<Favorite>>
    fun isFavorite(favorite: Favorite):Single<Boolean>

    fun saveDescription(description: String, favorite: Favorite) : Single<MutableList<Favorite>>

}