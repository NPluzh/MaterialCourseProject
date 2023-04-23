package geekbarains.material.model.map.coord.repocoord

import geekbarains.material.model.map.coord.entity.CapitalCoords
import io.reactivex.rxjava3.core.Single

interface ICoordsRepo {
    fun geCoordsOfCapital(
        capital: String?,keyApi: String?,units: String?, lang:String?): Single<CapitalCoords>
}