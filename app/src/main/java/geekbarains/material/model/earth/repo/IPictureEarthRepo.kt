package geekbarains.material.model.earth.repo

import geekbarains.material.model.earth.entity.PictureOfEarth
import io.reactivex.rxjava3.core.Single

interface IPictureEarthRepo {
    fun getPictureOfEarth(): Single<List<PictureOfEarth>>
}