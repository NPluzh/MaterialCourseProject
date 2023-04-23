package geekbarains.material.model.pictureofday.api

import geekbarains.material.model.pictureofday.entity.PODServerResponseData
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureOfTheDayAPI {

    @GET("planetary/apod")
    fun getPictureOfTheDay(
        @Query("api_key") apiKey: String,
        @Query("date") date: String
    ):Single<PODServerResponseData>

}
