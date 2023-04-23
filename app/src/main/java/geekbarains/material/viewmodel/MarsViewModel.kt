package geekbarains.material.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import geekbarains.material.App
import geekbarains.material.model.mars.api.ApiFactoryMars
import geekbarains.material.model.mars.entity.MarsSealed
import geekbarains.material.model.mars.repo.IMarsRepo
import geekbarains.material.model.mars.repo.MarsRepo
import geekbarains.material.model.pref.IPrefRepo
import geekbarains.material.model.pref.PrefRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class MarsViewModel:ViewModel() {

    companion object{
        const val TAG = "33333"
    }

    private val marsRepo: IMarsRepo = MarsRepo(ApiFactoryMars.API)
    private val marsSealed = MutableLiveData<MarsSealed>()

    val prefHelper : IPrefRepo = PrefRepo(App.instance)

    fun getData(): LiveData<MarsSealed>{
       // loadData()
        return marsSealed
    }

   fun  loadData(date:String, roverType:Int){
       Log.d(TAG, "### MarsViewModel loadData")
       val apiKey = "wX1Eamf7gnFJj4cgU1U1pl5LGNyxvuLhT7FQ9wPg"
       marsSealed.value = MarsSealed.Loading(null )

       when(roverType){
           1->marsRepo.getFotosOfMarsCuriosity(date, apiKey)
           2->marsRepo.getFotosOfMarsOpportunity(date, apiKey)
           3->marsRepo.getFotosOfMarsSpirit(date, apiKey)
           else->marsRepo.getFotosOfMarsCuriosity(date, apiKey)
       }
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe({
               marsSealed.value = MarsSealed.Success(marsResponseData = it )
           },{
               marsSealed.value = MarsSealed.Error(error = it)
           })
   }

    fun getDatePickerDateCur():String{
        return  prefHelper.getDatePickerDateCur()
    }
    fun getDatePickerDateOpp():String{
        return  prefHelper.getDatePickerDateOpp()
    }
    fun getDatePickerDateSpir():String{
        return  prefHelper.getDatePickerDateSpir()
    }

    fun saveDatePickerDateCur(date:String){
        prefHelper.saveDatePickerDateCur(date)
    }
    fun saveDatePickerDateOpp(date:String){
        prefHelper.saveDatePickerDateOpp(date)
    }
    fun saveDatePickerDateSpir(date:String){
        prefHelper.saveDatePickerDateSpir(date)
    }
}