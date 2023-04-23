package geekbarains.material.model.map.coord.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {

    val API : IDataSourceCoords

    init {
        API =
            getCoordsService()
     }

    private fun getCoordsService(): IDataSourceCoords {
        //Создаем  объект,  при  помощи  которого  будем  выполнять  запросы
        return Retrofit.Builder()
            //Базовая  часть  адреса
            .baseUrl("https://api.openweathermap.org/")
            //прикручиваем RxJava
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            //Конвертер,  необходимый  для  преобразования  JSON  в   объекты
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IDataSourceCoords::class.java)
    }

}