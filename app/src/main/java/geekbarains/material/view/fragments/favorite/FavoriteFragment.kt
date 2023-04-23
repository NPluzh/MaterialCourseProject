package geekbarains.material.view.fragments.favorite

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import geekbarains.material.R
import geekbarains.material.view.constants.Constants
import geekbarains.material.model.room.Favorite
import geekbarains.material.view.fragments.adapters.FavoriteRVAdapter
import geekbarains.material.viewmodel.PictureOfTheDayViewModel
import geekbarains.material.viewmodel.FavoriteViewModel
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment: Fragment() {

    private var adapter: FavoriteRVAdapter? = null
    private lateinit var favoriteViewModel: FavoriteViewModel
    private var isEdit = false
    lateinit var navController:NavController

    companion object {
        const val TAG = "33333"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        View.inflate(context, R.layout.fragment_favorite, null)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "FavoriteFragment onViewCreated ")

        navController = Navigation.findNavController(view)

        //разрешаем показ меню во фрагменте
        setHasOptionsMenu(true)

        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        favoriteViewModel.getFavorite()
            .observe(viewLifecycleOwner, Observer<MutableList<Favorite>>{ favorites->
            Log.d(TAG, "FavoriteFragment onViewCreated")
            favorites?. let{ renderData(it) }})

        initAdapter()

        //приводим меню тулбара в соответствии с onPrepareOptionsMenu в MainActivity
        setHasOptionsMenu(true)
        requireActivity().invalidateOptionsMenu()

        //если во всех холдерах одинаковый дивайдер, можно так
        //rv_favorite.addItemDecoration(DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL))
    }


    private fun renderData(favorites: MutableList<Favorite>) {
        if(favorites.isEmpty()){
            rv_favorite.visibility = View.GONE
            empty_view_favorite.visibility = View.VISIBLE
        }else{
            rv_favorite.visibility =  View.VISIBLE
            empty_view_favorite.visibility =View.GONE

            adapter?.listFavorites = favorites //передаём список в адаптер
        }
    }

    private fun initAdapter() {
        rv_favorite.layoutManager = LinearLayoutManager(requireActivity())

        adapter = FavoriteRVAdapter(
            getOnClickListener(),
            getRemoveListener(),
            getAddDescriptionListener()
        )
        rv_favorite.adapter = adapter
    }

    private fun getOnClickListener(): FavoriteRVAdapter.OnitemClickListener =
        object : FavoriteRVAdapter.OnitemClickListener{
            override fun onItemclick(favorite: Favorite) {
                Log.d(TAG, "FavoriteFragment setOnClickListener ")

                val bundle = bundleOf(
                    Constants.URL_ANIMATION to favorite.url,
                    Constants.MEDIA_TYPE_ANIMATION to favorite.type  ) //так проще
                navController.navigate(R.id.animationFragment, bundle)
            }
        }

    private fun getRemoveListener(): FavoriteRVAdapter.OnRemoveListener =
        object: FavoriteRVAdapter.OnRemoveListener{
            override fun onRemove(favorite: Favorite) {
                Log.d(TAG, "### ### FavoriteFragment getRemoveListener favorite.date = ${favorite.date}")
                val viewModel = ViewModelProvider(requireActivity()).get(PictureOfTheDayViewModel::class.java)
                viewModel.removeFavorite(favorite)
            }
        }

    private fun getAddDescriptionListener(): FavoriteRVAdapter.OnAddDescriptionListener =
        object : FavoriteRVAdapter.OnAddDescriptionListener{
            override fun onAddDescription(favorite: Favorite) {
                DialogAddDescription(getOnTransmitListener(), favorite)
                    .show(requireActivity().supportFragmentManager, "DialogAddDescription")
            }
        }

    private fun getOnTransmitListener(): DialogAddDescription.TransmitDescription =
        object : DialogAddDescription.TransmitDescription{
            override fun onTransmit(description: String, favorite: Favorite) {
                Log.d(TAG, "### ### FavoriteFragment getOnTransmitListener description = $description")
                favoriteViewModel.saveDescription(description, favorite)
            }
        }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_app_bar, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.app_bar_favorites).isVisible = false
        menu.findItem(R.id.app_bar_zoom).isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.app_bar_edit -> {
                isEdit = !isEdit
                adapter?.setEditType(isEdit)
                adapter?.notifyDataSetChanged()
            }

            R.id.app_bar_settings ->
                navController.navigate(R.id.settingsFragment)

            R.id.app_bar_search_wiki ->
                navController.navigate(R.id.searchFragment)

            R.id.app_bar_help ->
                navController.navigate(R.id.helpFragment)
        }
        return super.onOptionsItemSelected(item)
    }
}