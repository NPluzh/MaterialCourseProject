package geekbarains.material.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import geekbarains.material.App
import geekbarains.material.model.pictureofday.repo.RoomFavoriteCash
import geekbarains.material.model.room.Database
import geekbarains.material.model.room.Favorite
import geekbarains.material.model.pictureofday.entity.FavoriteSealed
import geekbarains.material.model.pictureofday.repo.IRoomFavoriteCash
import geekbarains.material.model.pictureofday.api.PODRetrofitImpl
import geekbarains.material.model.pictureofday.entity.PictureOfTheDaySealed
import geekbarains.material.model.pref.IPrefRepo
import geekbarains.material.model.pref.PrefRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class PictureOfTheDayViewModel : ViewModel() {

    private val isFavorite = MutableLiveData<FavoriteSealed>()
    private val roomCash: IRoomFavoriteCash =
        RoomFavoriteCash(Database.getInstance())

    private val liveDataForViewToObserve: MutableLiveData<PictureOfTheDaySealed> = MutableLiveData()
    private val retrofitImpl: PODRetrofitImpl =
        PODRetrofitImpl("https://api.nasa.gov/")

    val prefHelper :IPrefRepo = PrefRepo(App.instance)

    var date = ""

    companion object {
        const val TAG = "33333"
    }

    fun getData(): LiveData<PictureOfTheDaySealed> {
        //sendServerRequest(date)
        Log.d(TAG, "PictureOfTheDayViewModel getData ")
        return liveDataForViewToObserve
    }

    fun sendServerRequest(date:String) {
        liveDataForViewToObserve.value =
            PictureOfTheDaySealed.Loading(
                null
            )
        //val apiKey: String = BuildConfig.NASA_API_KEY
        val apiKey = "wX1Eamf7gnFJj4cgU1U1pl5LGNyxvuLhT7FQ9wPg"

        Log.d(TAG, "PictureOfTheDayViewModel sendServerRequest date = $date")

            retrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey, date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    Log.d(TAG, "PictureOfTheDayViewModel sendServerRequest Success date = ${it.date}")
                    liveDataForViewToObserve.value =
                        PictureOfTheDaySealed.Success(
                            serverResponseData = it
                        )
                },{error ->
                    Log.d(TAG, "PictureOfTheDayViewModel sendServerRequest Error")
                    if (error.message.isNullOrEmpty()){
                        liveDataForViewToObserve.value =
                            PictureOfTheDaySealed.Error(
                                Throwable("Unidentified error")
                            )
                    }else{
                        liveDataForViewToObserve.value =
                            PictureOfTheDaySealed.Error(
                                error = error
                            )
                    }
                })
    }

    fun isFavoriteState(favorite: Favorite): LiveData<FavoriteSealed>{
        roomCash.isFavorite(favorite)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isFavorite.value =
                    FavoriteSealed.Success(
                        isFavorite = it
                    )
            }, {
                isFavorite.value =
                    FavoriteSealed.Error(
                        error = it
                    )
                Log.d(TAG, "PictureOfTheDayViewModel isFavoriteState error = ${it.message} ")
            })
        return isFavorite
    }

    fun addToFavorite(favorite: Favorite){
        Log.d(TAG, "PictureOfTheDayViewModel addToFavorite ")
        roomCash.addToFavorite(favorite)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                isFavorite.value =
                    FavoriteSealed.Success(
                        isFavorite = true
                    )
            },{
                isFavorite.value =
                    FavoriteSealed.Error(
                        error = it
                    )
                Log.d(TAG, "PictureOfTheDayViewModel addToFavorite error = ${it.message} ")
            })
    }

    fun removeFavorite(favorite: Favorite){
        Log.d(TAG, "PictureOfTheDayViewModel removeFavorite ")
        roomCash.removeFavorite(favorite)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                isFavorite.value =
                    FavoriteSealed.Success(
                        isFavorite = false
                    )
            },{
                isFavorite.value =
                    FavoriteSealed.Error(
                        error = it
                    )
                Log.d(TAG, "PictureOfTheDayViewModel removeFavorite error = ${it.message} ")
            })
    }

   fun saveDatePickerDate(date:String){
       prefHelper.saveDatePickerDate(date)
   }
    fun getDatePickerDate():String{
      return  prefHelper.getDatePickerDate()
    }
}
