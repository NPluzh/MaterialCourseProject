package geekbarains.material.view.fragments.tabs.map

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import geekbarains.material.R
import geekbarains.material.view.constants.Constants
import geekbarains.material.model.map.states.entity.CapitalOfState
import geekbarains.material.model.map.coord.entity.CapitalCoords
import geekbarains.material.model.map.coord.entity.CoordSealed
import geekbarains.material.util.snackBarLong
import geekbarains.material.view.fragments.adapters.MapRecyclerAdapter
import geekbarains.material.viewmodel.MapViewModel
import kotlinx.android.synthetic.main.fragment_map.*
import java.util.*

class MapFragment : Fragment(){

    companion object{
        const val TAG = "33333"
    }
    lateinit var viewModelMap: MapViewModel
    private var adapter: MapRecyclerAdapter? = null
    private var temp  = 0
    private var capitalOfState = listOf<CapitalOfState>()
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map_start,container, false )

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        viewModelMap = ViewModelProvider(this).get(MapViewModel::class.java)
        //так как используются вкладки, а список на второй вкладке - для его отображения
        //используем LiveData, которая сама выдаст данные, когда перейдём на вкладку со списком
        //если делать не через LiveData? а просто получать список, он не отображается
        viewModelMap.loadData().observe(viewLifecycleOwner, Observer { list->
            Log.d(TAG, "MapFragment onViewCreated вкладка со списком " )
            adapter?.listCapitals = list
            capitalOfState = list //сохраняем список
        })

        initAdapter()

        //слушатель на изменение текста в поле поиска
        input_edit_text_map.addTextChangedListener { et->
            if (et.toString().isNotBlank()) {
                val listSearched = mutableListOf<CapitalOfState>()
                for (state in capitalOfState) {
                    state.name?. let{ name->
                        if((name.toUpperCase(Locale.ROOT)
                                .startsWith(et.toString().toUpperCase(Locale.ROOT)))){
                            listSearched.add(state)
                        }
                    }
                }
                adapter?.listCapitals = listSearched
            }else{
                adapter?.listCapitals = capitalOfState
            }
        }
        //слушатель на иконку  в конце поля поиска
        input_layout_map.setEndIconOnClickListener {
            input_edit_text_map.setText("") // очищаем поле и возвращаем исходный список
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "MapFragment onPause " )
    }

    private fun  initAdapter(){
    rv_map.layoutManager =LinearLayoutManager(requireActivity())
        adapter =
            MapRecyclerAdapter(
                getOnClickListener()
            )
        rv_map.adapter = adapter
    }

    private fun getOnClickListener(): MapRecyclerAdapter.OnitemClickListener =
        object : MapRecyclerAdapter.OnitemClickListener{
            override fun onItemclick(capitalOfState: CapitalOfState) {
                Log.d(TAG, "MapFragment getOnClickListener " +
                        "${capitalOfState.capital} ${capitalOfState.name}")

                viewModelMap.getCoordSealed(capitalOfState)
                    .observe(viewLifecycleOwner, Observer {renderData(it)})
            }
        }

    private fun renderData(data: CoordSealed) {
        when(data){
            is CoordSealed.Success ->{
                renderLoadingStop()
                renderCoords(data.capitalCoords)
            }
            is CoordSealed.Error -> {
                renderLoadingStop()
                renderError(data.error)
            }
            is CoordSealed.Loading -> {
                renderLoadingStart()
            }
        }
    }

    private fun renderCoords(capitalCoords: CapitalCoords){
        temp++
        val lat = capitalCoords.coord?.lat //широта для столицы государства
        val lon = capitalCoords.coord?.lon //долгота для столицы государства

        Log.d(TAG, "MapFragment renderCoords " +
                "Координаты для ${capitalCoords.name}  lon = $lon  lat = $lat  temp =$temp")

        val bundle = bundleOf(Constants.LAT to lat,  Constants.LON to lon) //так проще
        navController.navigate(R.id.mapsActivity, bundle)
    }

    private fun renderLoadingStart(){
       progressBarMap.visibility = View.VISIBLE
    }

    private fun renderLoadingStop(){
        progressBarMap.visibility = View.GONE
    }

    private fun renderError(error: Throwable) {
        snackBarLong(this@MapFragment.requireView(),"Ошибка $error")
    }
}