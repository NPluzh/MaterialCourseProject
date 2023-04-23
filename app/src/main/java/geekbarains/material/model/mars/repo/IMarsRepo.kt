package geekbarains.material.model.mars.repo

import geekbarains.material.model.earth.entity.PictureOfEarth
import geekbarains.material.model.mars.entity.FotosOfMars
import geekbarains.material.model.mars.entity.MarsResponseData
import io.reactivex.rxjava3.core.Single

interface IMarsRepo {
    fun getFotosOfMarsCuriosity(date:String, api_key:String): Single<MarsResponseData>
    fun getFotosOfMarsOpportunity(date:String, api_key:String): Single<MarsResponseData>
    fun getFotosOfMarsSpirit (date:String, api_key:String): Single<MarsResponseData>



}