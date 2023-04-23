package geekbarains.material.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import geekbarains.material.model.earth.api.ApiFactoryPictureOfEarth
import geekbarains.material.model.earth.repo.IPictureEarthRepo
import geekbarains.material.model.earth.repo.PictureEarthRepo
import geekbarains.material.model.earth.entity.PictureEarthSealed
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class EarthViewModel: ViewModel() {
    companion object{
        const val TAG = "33333"
    }

    //для получения картинки Земли
    private val pictureEarthSealed =  MutableLiveData<PictureEarthSealed>()
    private val pictureRepo: IPictureEarthRepo =
        PictureEarthRepo(ApiFactoryPictureOfEarth.API )

    fun getPictures():LiveData<PictureEarthSealed> {
        loadPictures()
        return pictureEarthSealed
    }

    private fun loadPictures() {
        Log.d(TAG, "### EarthViewModel loadPictures")
        pictureEarthSealed.value =
            PictureEarthSealed.Loading(
                null
            )
        pictureRepo.getPictureOfEarth()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                pictureEarthSealed.value =
                    PictureEarthSealed.Success(
                        listPicturesOfEarth = it
                    )
            },{
                pictureEarthSealed.value =
                    PictureEarthSealed.Error(
                        error = it
                    )
            })
    }


}