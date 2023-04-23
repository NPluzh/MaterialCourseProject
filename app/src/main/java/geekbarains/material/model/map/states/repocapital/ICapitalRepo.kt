package geekbarains.material.model.map.states.repocapital

import geekbarains.material.model.map.states.entity.CapitalOfState

interface ICapitalRepo {
    fun loadData(): List<CapitalOfState>
}