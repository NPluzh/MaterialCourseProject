package geekbarains.material.view.fragments.tabs.mars

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import geekbarains.material.R
import geekbarains.material.model.mars.entity.MarsResponseData
import geekbarains.material.model.mars.entity.MarsSealed
import geekbarains.material.util.snackBarLong
import geekbarains.material.view.constants.Constants
import geekbarains.material.view.constants.Constants.CURIOSITY
import geekbarains.material.view.constants.Constants.OPPORTUNITY
import geekbarains.material.view.constants.Constants.SPIRIT
import geekbarains.material.view.fragments.adapters.MarsRVAdapter
import geekbarains.material.viewmodel.MarsViewModel
import kotlinx.android.synthetic.main.fragment_mars.*

class MarsFragment: Fragment(), DatePickerFragmentRover.OnItemClickListenerRover {

    companion object{
        const val TAG = "33333"
    }

    var dateCuriocity = "2015-6-3"
    var dateOpportunity = "2015-6-3"
    var dateSpirit = "2005-6-3"

    var roverType = CURIOSITY

    private lateinit var viewModelMars: MarsViewModel
    private var adapter: MarsRVAdapter? = null
    lateinit var navController: NavController

    //метод интерфейса DatePickerFragment
    override fun onItemClick(date: String, typeRover: Int) {
        when(typeRover){
            CURIOSITY->{
                viewModelMars.saveDatePickerDateCur(date)
                chip1Mars.isChecked = true
            }
            OPPORTUNITY->{
                viewModelMars.saveDatePickerDateOpp(date)
                chip2Mars.isChecked = true
            }
            SPIRIT->{
                viewModelMars.saveDatePickerDateSpir(date)
                chip3Mars.isChecked = true
            }
        }
        viewModelMars. loadData(date, typeRover)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mars, container, false )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        viewModelMars = ViewModelProvider(requireActivity()).get(MarsViewModel::class.java)
        viewModelMars.loadData(dateCuriocity, CURIOSITY)
        chip1Mars.isChecked = true

        viewModelMars.getData().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })

        initAdapter()
        initViews()
    }

   fun initViews(){
       chip1Mars.setOnClickListener {
           roverType = CURIOSITY
           viewModelMars.loadData(dateCuriocity, roverType)
       }
       chip2Mars.setOnClickListener {
           roverType = OPPORTUNITY
           viewModelMars.loadData(dateOpportunity, roverType)
       }
       chip3Mars.setOnClickListener {
           roverType = SPIRIT
           viewModelMars.loadData(dateSpirit, roverType)
       }
       chip4Mars.setOnClickListener {
           when(roverType){
               CURIOSITY->{
                   DatePickerFragmentRover(this, viewModelMars.getDatePickerDateCur(),CURIOSITY )
               }
               OPPORTUNITY->{
                   DatePickerFragmentRover(this, viewModelMars.getDatePickerDateOpp(), OPPORTUNITY)
               }
               SPIRIT->{
                   DatePickerFragmentRover(this, viewModelMars.getDatePickerDateSpir(), SPIRIT)
               }
               else-> {
                   DatePickerFragmentRover(this, viewModelMars.getDatePickerDateCur(),CURIOSITY )
               }}
               .show(childFragmentManager, "DatePickerRover")
       }
   }


    private fun renderData(data: MarsSealed) {
        when(data){
            is MarsSealed.Success ->{
                renderLoadingStop()
                renderMarsFotos(data.marsResponseData)
            }
            is MarsSealed.Error -> {
                renderLoadingStop()
                renderError(data.error)
            }
            is MarsSealed.Loading -> {
                renderLoadingStart()
            }
        }
    }

    private fun renderMarsFotos(data: MarsResponseData){
        data.photos?. let{
            Log.d(TAG, "### MarsFragment renderMarsFotos  photos.size = ${it.size}")
            adapter?.data = it
        }
    }

    private fun renderLoadingStart(){
        progressBarMars.visibility = View.VISIBLE
    }

    private fun renderLoadingStop(){
        progressBarMars.visibility = View.GONE
    }

    private fun renderError(error: Throwable) {
        snackBarLong(this.requireView(),
            "Сегодня нет изображений со спутника \n " +
                    "Ошибка $error")
    }

    private fun  initAdapter(){
        rv_mars.layoutManager = LinearLayoutManager(requireActivity())
        adapter = MarsRVAdapter(getOnClickListener())
        rv_mars.adapter = adapter
    }

    fun getOnClickListener(): MarsRVAdapter.OnItemClickListener=
        object : MarsRVAdapter.OnItemClickListener {
            override fun onItemClick(url: String) {
                Log.d(TAG, "### MarsFragment getOnClickListener $url")

               val  newUrl = url.replace("http", "https")
                val bundle = bundleOf(
                    Constants.URL_ANIMATION to newUrl,
                    Constants.MEDIA_TYPE_ANIMATION to Constants.MEDIA_NONE)
                navController.navigate(R.id.animationFragment, bundle)
            }
        }




}