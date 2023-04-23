package geekbarains.material.model.map.coord.repocoord

import geekbarains.material.model.map.coord.entity.CapitalCoords
import geekbarains.material.model.map.coord.api.IDataSourceCoords
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class CoordsRepo(val api: IDataSourceCoords):
    ICoordsRepo {

    override fun geCoordsOfCapital(capital: String?,keyApi: String?,
        units: String?,lang: String?): Single<CapitalCoords> =

        api.loadCoordsOfCapitalRu(capital, keyApi, units, lang )
            .subscribeOn(Schedulers.io())
}