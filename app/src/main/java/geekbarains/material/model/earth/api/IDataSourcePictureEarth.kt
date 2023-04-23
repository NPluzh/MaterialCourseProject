package geekbarains.material.model.earth.api

import geekbarains.material.model.earth.entity.PictureOfEarth
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface IDataSourcePictureEarth {

    @GET("api/natural"  )
    fun getPictureOfEarth(): Single<List<PictureOfEarth>>
}