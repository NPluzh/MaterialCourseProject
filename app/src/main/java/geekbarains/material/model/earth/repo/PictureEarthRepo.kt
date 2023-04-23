package geekbarains.material.model.earth.repo

import geekbarains.material.model.earth.api.IDataSourcePictureEarth
import geekbarains.material.model.earth.entity.PictureOfEarth
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class PictureEarthRepo(val api: IDataSourcePictureEarth):
    IPictureEarthRepo {

    override fun getPictureOfEarth(): Single<List<PictureOfEarth>> {
            return   api.getPictureOfEarth().subscribeOn(Schedulers.io())
    }
}