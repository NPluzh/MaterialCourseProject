package geekbarains.material.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import geekbarains.material.model.room.Database
import geekbarains.material.model.room.Favorite
import geekbarains.material.model.pictureofday.repo.IRoomFavoriteCash
import geekbarains.material.model.pictureofday.repo.RoomFavoriteCash
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class FavoriteViewModel: ViewModel() {
    companion object{
        const val TAG = "33333"
    }

    private  var listFavoriteStates = MutableLiveData<MutableList<Favorite>>()
    private val roomCash: IRoomFavoriteCash =
        RoomFavoriteCash(Database.getInstance())

    fun getFavorite(): LiveData<MutableList<Favorite>> {
        loadFavorite()
        return listFavoriteStates

    }

    private fun loadFavorite() {
        roomCash. loadFavorite()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({favorites->
                //обновляем список в случае его изменения
                favorites?. let{
                    Log.d(TAG, "FavoriteViewModel loadFavorite: Favorite.size =  ${it.size}")}
                listFavoriteStates.value = favorites
            }, {error ->
                Log.d(TAG, "FavoriteViewModel onError ${error.message}")
            })
    }


    fun saveDescription(description: String, favorite: Favorite){
        roomCash. saveDescription(description, favorite)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({favorites->
                //обновляем список в случае его изменения
                favorites?. let{
                    Log.d(TAG, "FavoriteViewModel saveDescription: Favorite.size =  ${it.size}")}
                listFavoriteStates.value = favorites
            }, {error ->
                Log.d(TAG, "FavoriteViewModel onError ${error.message}")
            })
    }
}