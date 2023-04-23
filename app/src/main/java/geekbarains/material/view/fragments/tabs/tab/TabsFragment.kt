package geekbarains.material.view.fragments.tabs.tab

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import geekbarains.material.R
import geekbarains.material.view.constants.Constants
import geekbarains.material.view.fragments.tabs.pictureofday.PictureOfTheDayFragment
import kotlinx.android.synthetic.main.fragment_tabs.*

class TabsFragment:Fragment() {

    companion object{
        const val TAG = "33333"
    }

    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tabs,container, false )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "**-** TabsFragment onViewCreated  ")

        navController = Navigation.findNavController(view)

        //разрешаем показ меню во фрагменте
        setHasOptionsMenu(true)

        view_pager.adapter =
            ViewPageAdapter(
                childFragmentManager
            )
        tab_layout.setupWithViewPager(view_pager)
        //устанавливаем текущую вкладку - если в аргументах  ничего нет - фото дня
        val tabPosition = arguments?.getInt(Constants.PAGER_POSITION, 0)
        Log.d(TAG, "**-** TabsFragment onViewCreated  tabPosition = $tabPosition")
        tabPosition?. let{
            view_pager.currentItem = it
        }?: let{view_pager.currentItem = 0}
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_app_bar, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.app_bar_edit).isVisible = false
        menu.findItem(R.id.app_bar_zoom).isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_favorites ->
                navController.navigate(R.id.favoriteFragment)

            R.id.app_bar_search_wiki ->
                navController.navigate(R.id.searchFragment)

            R.id.app_bar_settings ->
                navController.navigate(R.id.settingsFragment)

            R.id.app_bar_help ->
                navController.navigate(R.id.helpFragment)
        }
        return super.onOptionsItemSelected(item)
    }

}