package geekbarains.material.model.map.states.repocapital

import geekbarains.material.model.map.states.entity.CapitalOfState
import geekbarains.material.model.map.states.entity.MapOfCapital

class CapitalRepo:
    ICapitalRepo {

    override fun loadData(): List<CapitalOfState> {
        val list =   mutableListOf<CapitalOfState>()
        for (mapp in MapOfCapital.mapCapitals){
            list.add(
                CapitalOfState(
                    name = mapp.key,
                    capital = mapp.value
                )
            )
        }
     return  list
    }
}