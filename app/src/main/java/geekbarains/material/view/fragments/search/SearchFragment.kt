package geekbarains.material.view.fragments.search

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.preference.PreferenceManager
import com.google.android.material.chip.Chip
import geekbarains.material.R
import geekbarains.material.util.toast
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment(){

    companion object{
        const val TAG = "33333"
        const val LAST_STRING ="LAST_STRING"
    }

    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search,container, false )

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d( TAG, "SearchFragment onViewCreated backStackEntryCount =" +
                "${requireActivity().supportFragmentManager.backStackEntryCount}" )

        navController = Navigation.findNavController(view)

        //разрешаем показ меню во фрагменте
        setHasOptionsMenu(true)
        //слушатель на чипсы
        initChipGroup()
        //слушатель на иконку строки поиска
        input_layout_search.setEndIconOnClickListener {
            if (input_edit_text_search.text.toString().isNotBlank()){
                startActivity(Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("https://ru.wikipedia.org/wiki/${input_edit_text_search.text.toString()}")
                })
            }else{
                toast(resources.getString(R.string. input_any_text))
            }
        }
    }

    override fun onPause() {
        super.onPause()
        //запоминаем строку ввода
        PreferenceManager.getDefaultSharedPreferences(requireActivity())
            .edit()
            .putString(LAST_STRING, input_edit_text_search.text.toString())
            .apply()
    }

    private fun initChipGroup() {
        chipGroupSearch.setOnCheckedChangeListener { group, id ->
            group?. let{
                Log.d(TAG, "SearchFragment initChipGroup id = $id")
                if(id >= 0){ //при повторном щелчке id = -1
                    val chip: Chip = group.findViewById(id)
                    input_edit_text_search.setText(chip.text.toString())
                    Log.d(TAG, "SearchFragment initChipGroup id = $id  chip.text = ${chip.text}")
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_app_bar, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.app_bar_search_wiki).isVisible = false
        menu.findItem(R.id.app_bar_edit).isVisible = false
        menu.findItem(R.id.app_bar_favorites).isVisible = false
        menu.findItem(R.id.app_bar_zoom).isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_settings ->
              navController.navigate(R.id.settingsFragment)
            R.id.app_bar_help ->
                navController.navigate(R.id.helpFragment)
        }
        return super.onOptionsItemSelected(item)
    }
}