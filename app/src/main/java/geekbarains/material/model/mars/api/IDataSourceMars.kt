package geekbarains.material.model.mars.api

import geekbarains.material.model.earth.entity.PictureOfEarth
import geekbarains.material.model.mars.entity.FotosOfMars
import geekbarains.material.model.mars.entity.MarsResponseData
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface IDataSourceMars {

    @GET("curiosity/photos"  )
    fun getFotosOfMarsCuriosity(
    @Query("earth_date") date: String,
    @Query("api_key") apiKey: String
    ): Single<MarsResponseData>

    @GET("opportunity/photos"  )
    fun getFotosOfMarsOpportunity(
        @Query("earth_date") date: String,
        @Query("api_key") apiKey: String
    ): Single<MarsResponseData>

    @GET("spirit/photos"  )
    fun getFotosOfMarsSpirit(
        @Query("earth_date") date: String,
        @Query("api_key") apiKey: String
    ): Single<MarsResponseData>


}