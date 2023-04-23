package geekbarains.material.view.fragments.tabs.earth

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
import geekbarains.material.view.constants.Constants
import geekbarains.material.model.earth.entity.PictureEarthSealed
import geekbarains.material.model.earth.entity.PictureOfEarth
import geekbarains.material.util.snackBarLong
import geekbarains.material.view.fragments.adapters.EarthRecyclerAdapter
import geekbarains.material.viewmodel.EarthViewModel
import kotlinx.android.synthetic.main.fragment_earth.*

class EarthFragment: Fragment() {

    companion object{
        const val TAG = "33333"
    }

    private lateinit var viewModelEarth: EarthViewModel
    private var adapter: EarthRecyclerAdapter? = null
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_earth,container, false )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        viewModelEarth =ViewModelProvider(requireActivity()).get(EarthViewModel::class.java)

        initAdapter()

        viewModelEarth.getPictures().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })
    }

    private fun renderData(data: PictureEarthSealed) {
        when(data){
            is PictureEarthSealed.Success ->{
                renderLoadingStop()
                renderPicture(data.listPicturesOfEarth)
            }
            is PictureEarthSealed.Error -> {
                renderLoadingStop()
                renderError(data.error)
            }
            is PictureEarthSealed.Loading -> {
                renderLoadingStart()
            }
        }
    }

    private fun renderPicture(data: List<PictureOfEarth>){
        adapter?.listOfPictures = data
    }

    private fun renderLoadingStart(){
        progressBarEarth.visibility = View.VISIBLE
    }

    private fun renderLoadingStop(){
        progressBarEarth.visibility = View.GONE
    }

    private fun renderError(error: Throwable) {
        snackBarLong(this.requireView(),
            "Сегодня нет изображений со спунника \n " +
                    "Ошибка $error")
    }

    private fun  initAdapter(){
        rv_earth.layoutManager = LinearLayoutManager(requireActivity())
        adapter =
            EarthRecyclerAdapter(
                getOnClickListener()
            )
        rv_earth.adapter = adapter
    }

    private fun getOnClickListener(): EarthRecyclerAdapter.OnItemClickListener =
        object : EarthRecyclerAdapter.OnItemClickListener{
            override fun onItemClick(url: String) {
                Log.d(TAG, "### EarthFragment getOnClickListener $url")

                val bundle = bundleOf(Constants.URL_ANIMATION to url,
                    Constants.MEDIA_TYPE_ANIMATION to "image")
                navController.navigate(R.id.animationFragment, bundle)
            }
        }

}