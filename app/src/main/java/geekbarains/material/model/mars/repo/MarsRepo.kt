package geekbarains.material.model.mars.repo

import geekbarains.material.model.mars.api.IDataSourceMars
import geekbarains.material.model.mars.entity.MarsResponseData
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class MarsRepo(val api: IDataSourceMars):    IMarsRepo {

    override fun getFotosOfMarsCuriosity(date:String, api_key:String): Single<MarsResponseData> {
    return api.getFotosOfMarsCuriosity(date, api_key)
        .observeOn(Schedulers.io())
    }

    override fun getFotosOfMarsOpportunity(
        date: String,
        api_key: String
    ): Single<MarsResponseData> {
        return api.getFotosOfMarsOpportunity(date, api_key)
            .observeOn(Schedulers.io())
    }

    override fun getFotosOfMarsSpirit(date: String, api_key: String): Single<MarsResponseData> {
        return api.getFotosOfMarsSpirit(date, api_key)
            .observeOn(Schedulers.io())
    }


}